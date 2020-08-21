package gg.freedomsite.freedom.handlers;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.command.BlankCommand;
import gg.freedomsite.freedom.command.FreedomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Field;
import java.util.*;

public class CommandHandler
{

    public List<FreedomCommand> freedomCommandList = new ArrayList<>();

    public void register()
    {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(Freedom.get().getClass().getClassLoader());

        Reflections freedomReflections = new Reflections(new ConfigurationBuilder()
            .setScanners(new SubTypesScanner(false), new ResourcesScanner())
            .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
            .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("gg.freedomsite.freedom.command.impl"))));

        Set<Class<? extends FreedomCommand>> freedomClasses = freedomReflections.getSubTypesOf(FreedomCommand.class);

        for (Class<? extends FreedomCommand> freedomclass: freedomClasses)
        {
            try {
                FreedomCommand command = freedomclass.newInstance();
                if (command.isEnabled())
                {
                    getCommandMap().register(Freedom.get().getDescription().getName().toLowerCase(), new BlankCommand(command));
                    freedomCommandList.add(command);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregister()
    {
        for (FreedomCommand cmd : freedomCommandList)
        {
            getCommandMap().getCommand(cmd.getName()).unregister(getCommandMap());
            unregister(cmd);
        }

    }

    private static Object getPrivateField(org.bukkit.plugin.PluginManager object, String field) throws SecurityException,
        NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        
        return result;
    }


    public static void unregister(FreedomCommand cmd) {
        try {
            Object result = getPrivateField(Freedom.get().getServer().getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            Field knownCommandsField = commandMap.getClass().getField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);
            Bukkit.getLogger().info(String.valueOf(knownCommands.size()));
            knownCommands.get(cmd.getName()).getAliases();
            if (knownCommands.get(cmd.getName()).getAliases().size() > 0)
            {
                for (String als : knownCommands.get(cmd.getName()).getAliases())
                {
                    if(knownCommands.containsKey(als) && knownCommands.get(als).getAliases().contains(als))
                    {
                        knownCommands.remove(als);
                        knownCommands.remove(Freedom.get().getDescription().getName().toLowerCase() + ":" + als);
                    }
                }
            }
            knownCommands.remove(cmd.getName());
            knownCommands.remove(Freedom.get().getDescription().getName().toLowerCase() + ":" + cmd.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandMap getCommandMap()
    {
        Field bukkitCommandMap = null;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assert bukkitCommandMap != null;
        bukkitCommandMap.setAccessible(true);

        try {
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            return commandMap;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }




}
