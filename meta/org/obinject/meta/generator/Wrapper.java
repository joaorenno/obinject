/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 *
 * @author windows
 */
public class Wrapper {

    private static final MetaclassProvider metaProvider = new MetaclassProvider();
    private static final StandardJavaFileManager fileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null);

    private Wrapper() {

    }

    public static void create(String pack) {
        String[] split;
        String fullClassName;
        JavaFileObject javaFileObject;
        List<Class> listClass = new ArrayList<>();
        Iterator<? extends JavaFileObject> it;
        try {
            it = fileManager.list(
                    StandardLocation.CLASS_PATH, pack,
                    Collections.singleton(JavaFileObject.Kind.CLASS), false).iterator();
            while (it.hasNext()) {
                javaFileObject = it.next();
                split = javaFileObject.getName()
                        .replace(".class", "")
                        .replace(")", "")
                        .split(Pattern.quote(File.separator));
                fullClassName = pack + "." + split[split.length - 1];
                listClass.add(Class.forName(fullClassName));
            }
            for (Class clazz : listClass) {
                metaProvider.addMetaClasses(clazz);
            }
            metaProvider.save();
        } catch (IOException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
