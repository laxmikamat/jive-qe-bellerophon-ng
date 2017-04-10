package com.aurea;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.eclipse.jgit.api.Git;

import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Understand;

public class Udb {

  public static void main(String[] args) throws Exception {
    System.out.println(System.getenv());
    Understand.loadNativeLibrary();
    Database db = Understand.open("/Users/pbielicki/dev/scitools/bin/macosx/journals.udb");
    
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

    for (Entity o : db.ents("class, function")) {
      System.out.println(o.name() + ":" + Arrays.toString(o.metrics()));
    }
  }

}
