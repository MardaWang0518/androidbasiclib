package com.hxh.aspjplugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

/**
 * @author 侯小虎
 * @time 2017/6/20
 * @使用ajc编译java代码 ，ajc用于编译aspj代码，要想使用ajc，只能在gradle中配置，加入一些任务，使gradle支持ajc编译
 * 在 gradle 的编译 task 中增加额外配置，使之能正确编译运行。
 * application和lib的配置不一样,具体可参考
 * http://blog.csdn.net/innost/article/details/49387395  文末有将gradle配置成ajc编译器的代码
 * 然后为了方便，封装了一个gradle plugin ，自定义gradle plugin可参考：
 * https://juejin.im/entry/577bc26e165abd005530ead8
 * 此外还可以参考 Hugo项目：
 * https://github.com/JakeWharton/hugo/blob/master/hugo-plugin/src/main/groovy/hugo/weaving/plugin/HugoPlugin.groovy
 * 以及国内的沪江出品的:
 * https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx/blob/master/aspectjx/src/main/groovy/com/hujiang/gradle/plugin/android/aspectjx/AspectTransform.groovy
 * 这俩个项目可以来回学习
 */
public class AspectjPlugin implements Plugin<Project> {

    void apply(Project project) {
        //得到当前module的插件类型，是application还是lib
        System.out.println("========================");
        System.out.println("Aspject开始编译!");
        System.out.println("========================");

        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        project.dependencies {
            // TODO this should come transitively
            compile 'org.aspectj:aspectjrt:1.8.10'
        }

        variants.all {
            variant ->

            JavaCompile javaCompile = variant.javaCompile
            javaCompile.doLast {
                String[] args = ["-showWeaveInfo",
                                 "-1.8",
                                 "-inpath", javaCompile.destinationDir.toString(),
                                 "-aspectpath", javaCompile.classpath.asPath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true);
                new Main().run(args, handler);
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break;
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break;
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break;
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break;
                    }
                }
            }
        }

        System.out.println("========================");
        System.out.println("Aspject编译结束!");
        System.out.println("========================");

    }
}