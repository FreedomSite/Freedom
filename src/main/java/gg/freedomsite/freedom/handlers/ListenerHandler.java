package gg.freedomsite.freedom.handlers;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.command.BlankCommand;
import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.listeners.FreedomListener;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ListenerHandler
{

    public void register()
    {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(Freedom.get().getClass().getClassLoader());

        Reflections mechanicReflections = new Reflections(new ConfigurationBuilder()
            .setScanners(new SubTypesScanner(false), new ResourcesScanner())
            .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
            .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("gg.freedomsite.freedom.listeners.impl"))));

        Set<Class<? extends FreedomListener>> listenerClasses = mechanicReflections.getSubTypesOf(FreedomListener.class);

        for (Class<? extends FreedomListener> listenerclass: listenerClasses)
        {
            try {
                FreedomListener listener = listenerclass.newInstance();
                Freedom.get().getServer().getPluginManager().registerEvents(listener, Freedom.get());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
