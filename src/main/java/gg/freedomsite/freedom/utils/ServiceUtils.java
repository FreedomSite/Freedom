package gg.freedomsite.freedom.utils;

import com.google.common.reflect.ClassPath;
import gg.freedomsite.freedom.Freedom;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceUtils
{

    public static List<ClassPath.ClassInfo> getClasses(String packageName, String ending)
    {
        try {
            List<ClassPath.ClassInfo> classInfos = ClassPath.from(Freedom.get().getClass().getClassLoader())
                  .getAllClasses()
                  .stream()
                  .filter(classInfo -> classInfo.getPackageName().startsWith(packageName))
                  .filter(classInfo -> classInfo.getSimpleName().endsWith(ending))
                  .collect(Collectors.toList());
            return classInfos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
