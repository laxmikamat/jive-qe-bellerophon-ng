package com.aurea;

import java.util.Arrays;

import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Understand;

public class Udb {

    public static void main(final String[] args) throws Exception {
        System.out.println(System.getenv());
        Understand.loadNativeLibrary();
        final Database db = Understand.open("/Users/pbielicki/dev/scitools/bin/macosx/journals.udb");

        //    Path gitRepo = Files.createTempDirectory("gitrepo");
        //    Git git = Git.cloneRepository()
        //        .setURI("git@github.com:pbielicki/journals.git")
        //        .setDirectory(gitRepo.toFile())
        //        .call();
        //    
        //    IOFileFilter filter = new AbstractFileFilter() {
        //      @Override
        //      public boolean accept(File file) {
        //        return true;
        //      }
        //    };
        //    
        //    for (File file : FileUtils.listFilesAndDirs(gitRepo.toFile(), filter, filter)) {
        //      System.out.println(file);
        //    }

        //    GraphDatabaseService gdb = new GraphDatabaseFactory().newEmbeddedDatabase(File.createTempFile("neo4j", "db"));
        //    try (Transaction tx = gdb.beginTx()) {
        //      // Database operations go here
        //      tx.success();
        //    } catch (Exception ex) {
        //      // TODO
        //    }

        for (final Entity o : db.ents("class, function")) {
            System.out.println(o.name() + ":" + Arrays.toString(o.metrics()));
        }
    }

}
