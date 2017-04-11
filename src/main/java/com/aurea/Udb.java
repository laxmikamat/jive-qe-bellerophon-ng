package com.aurea;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import com.scitools.understand.Understand;

public class Udb {
    private static final String CLASS = "Class";
    private static final String PARAMETER = "Parameter";
    private static final String VARIABLE = "Variable";
    private static final String METHOD = "Method";
    private static final String DEFINE = "Define";
    private static final String PACKAGE = "Package";
    private static final String PROTECTED = "Protected";
    private static final String PUBLIC = "Public";
    private static final String ENUM_TYPE = "Enum Type";
    private static final String PUBLIC_ENUM_TYPE = PUBLIC + " " + ENUM_TYPE;
    private static final String PROTECTED_ENUM_TYPE = PROTECTED + " " + ENUM_TYPE;
    private static final Logger LOG = LoggerFactory.getLogger(Udb.class);
    private static final SortedSetMultimap<String, CodeReference> unused = TreeMultimap.create();
    private static final String INDENTATION = "    ";
    private static final String USE = INDENTATION + "Use";
    private static final String CALL = INDENTATION + "Call";
    private static final String DOT_REF = INDENTATION + "DotRef";
    private static Pass pass = Pass.INITIAL;

    static enum Pass {
        INITIAL, ANALYSIS
    }

    static class CodeReference implements Comparable<CodeReference> {
        private final String name;
        private final String type;
        private final String parent;

        public CodeReference(final Reference ref) {
            this.name = ref.ent().longname(true);
            this.type = ref.ent().kind().name();
            this.parent = ref.scope().longname(true);
        }

        public CodeReference(final String name) {
            this.name = name;
            this.type = null;
            this.parent = null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof CodeReference)) {
                return false;
            }

            final CodeReference other = (CodeReference) obj;
            return Objects.equals(name, other.name);
        }

        @Override
        public String toString() {
            return name + "\t" + type;
        }

        @Override
        public int compareTo(final CodeReference o) {
            return name.compareTo(o.name);
        }
    }

    public static void main(final String[] args) throws Exception {
        Understand.loadNativeLibrary();
        final Database db = Understand.open("/Users/pbielicki/dev/scitools/bin/macosx/unused.udb");
        final Entity[] pckgs = db.ents(PACKAGE + " ~unused ~unresolved");
        processPackages(pckgs);
        pass = Pass.ANALYSIS;
        processPackages(pckgs);

        unused.asMap().keySet().stream().forEach(key -> {
            System.out.println(key);
            unused.get(key).stream()
            .map(value -> "\t" + value)
            .forEach(System.out::println);   
        });
        System.out.println(unused.size());
    }

    private static void processPackages(final Entity[] pckgs) {
        for (final Entity pckg : pckgs) {
            processClasses(pckg);
        }
    }

    private static void processClasses(final Entity pckg) {
        final String pckgName = pckg.name();
        for (final Reference clazz : pckg.refs("~unresolved ~unknown", CLASS, true)) {
            final String className = clazz.ent().longname(true);
            final Entity file = clazz.file();
            checkReference(file, clazz);
            processInnerClass(pckgName, clazz);
            processMethods(pckgName, className, file, clazz.ent().refs(DEFINE, METHOD, true));
            processClassVariables(pckgName, className, file, clazz.ent().refs(DEFINE, VARIABLE, true));
        }

    }

    private static void processInnerClass(final String pckgName, final Reference clazz) {
        final Reference[] innerClasses = clazz.ent().refs(DEFINE + " ~unresolved ~unknown", CLASS + " ~TypeVariable", true);
        for (final Reference innerClass : innerClasses) {
            final String className = innerClass.ent().longname(true);
            final Entity file = innerClass.file();
            checkReference(file, innerClass);
            processMethods(pckgName, className, file, innerClass.ent().refs(DEFINE, METHOD, true));
            processClassVariables(pckgName, className, file, innerClass.ent().refs(DEFINE, VARIABLE, true));
        }
    }

    private static void checkReference(final Entity file, final Reference ref) {
        switch (pass) {
            case INITIAL:
                if (canSkip(ref)) {
                    break;
                }

                unused.put(file.name(), new CodeReference(ref));
                break;

            case ANALYSIS:
                checkUsages(file, ref);
                break;
            default:
                break;
        }
    }

    private static void processMethods(final String pckgName, final String className, final Entity file,
            final Reference[] methods) {
        for (final Reference method : methods) {
            final String methodName = method.ent().longname(true);
            processParamsAndVariables(pckgName, className, methodName, file, method.ent().refs(DEFINE, PARAMETER + " " + VARIABLE, true));
            checkReference(file, method);
        }
    }

    private static void processParamsAndVariables(final String pckgName, final String className, final String methodName,
            final Entity file, final Reference[] params) {
        for (final Reference param : params) {
            checkReference(file, param);
        }
    }

    private static void processClassVariables(final String pckgName, final String className, final Entity file,
            final Reference[] variables) {
        for (final Reference var : variables) {
            checkReference(file, var);
        }
    }

    private static boolean canSkip(final Reference ref) {
        return isPublicOrProtected(ref) || isFromPublicOrProtectedEnum(ref);
    }

    private static boolean isPublicOrProtected(final Reference ref) {
        final String kindName = ref.ent().kind().name();
        final String parentKindName = ref.scope().kind().name();
        return (kindName.contains(PUBLIC) || kindName.contains(PROTECTED))
                && (parentKindName.contains(PUBLIC) 
                        || parentKindName.contains(PROTECTED)
                        || parentKindName.contains(PACKAGE));
    }

    private static boolean isFromPublicOrProtectedEnum(final Reference ref) {
        final String parentKindName = ref.scope().kind().name();
        return parentKindName.contains(PUBLIC_ENUM_TYPE) 
                || parentKindName.contains(PROTECTED_ENUM_TYPE)
                || Arrays.stream(ref.scope().refs("", "", true))
                .map(parent -> parent.ent().kind().name())
                .anyMatch(kindName -> kindName.contains(PUBLIC_ENUM_TYPE) 
                        || kindName.contains(PROTECTED_ENUM_TYPE));
    }

    private static void checkUsages(final Entity file, final Reference ref) {
        final String kindName = ref.ent().kind().name();
        final String refName = ref.ent().longname(true);
        if (canSkip(ref)) {
            LOG.debug("Skipping " + kindName + " " + refName);
            return;
        }

        final List<String> references = Arrays.stream(ref.ent().ib(""))
                .filter(s -> s.startsWith(USE) 
                        || s.startsWith(CALL)
                        || s.startsWith(DOT_REF))
                .map(String::trim)
                .collect(Collectors.toList());

        if (!references.isEmpty()) {
            LOG.debug(kindName + " " + refName + " is referenced by " + references.toString());
            unused.get(file.name()).remove(new CodeReference(refName));
        }
    }
}
