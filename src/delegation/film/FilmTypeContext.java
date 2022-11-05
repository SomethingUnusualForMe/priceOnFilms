package delegation.film;

import delegation.film.general.FilmInfo;
import delegation.film.general.IFilm;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilmTypeContext
{
    private static FilmTypeContext CONTEXT = null;
    private final String TYPES_PACKAGE = "delegation.film.types";
    private HashMap<Class, IFilm> _Singletons;
    private FilmTypeContext()
    {
        init();
    }

    protected static FilmTypeContext instance()
    {
        if (CONTEXT == null)
            CONTEXT = new FilmTypeContext();

        return CONTEXT;
    }

    protected <T extends IFilm> void install(Class<T> filmTypeClass, T object)
    {
        _Singletons.put(filmTypeClass, object);
    }

    protected <T extends IFilm> T get(Class<T> filmTypeClass)
    {
        return (T) _Singletons.get(filmTypeClass);
    }

    private void init()
    {
        _Singletons = new HashMap<>();
        installFilmTypes();
    }

    private void installFilmTypes()
    {
        List<Class<IFilm>> filmTypesList = findFilmTypes();
        for (Class<IFilm> filmType : filmTypesList)
            install(filmType, newInstance(filmType));
    }

    private <T extends IFilm> List<Class<T>> findFilmTypes()
    {
        try
        {
            return getAnnotatedClasses(TYPES_PACKAGE, FilmInfo.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // reflections
    private <T> List<Class<T>> getAnnotatedClasses(String packagePath, Class<?> annotationClass) throws ClassNotFoundException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        URL urls                = classLoader.getResource(packagePath.replace('.', '/'));
        File folder             = new File(urls.getPath());
        File[]      classes     = folder.listFiles();
        int         size        = classes.length;
        List<Class<T>> classList = new ArrayList<>();

        for(int i=0;i<size;i++)
        {
            int             index           = classes[i].getName().indexOf(".");
            String          className       = classes[i].getName().substring(0, index);
            String          classNamePath   = packagePath+"."+className;
            Class<T>        repoClass       = (Class<T>) Class.forName(classNamePath);
            Annotation[]    annotations     = repoClass.getAnnotations();
            for(int j =0;j<annotations.length;j++)
                if (annotations[j].annotationType() == annotationClass)
                    classList.add(repoClass);
        }
        return classList;
    }

    private <T extends IFilm> T newInstance(Class<T> clazz)
    {
        T entity;
        Constructor<?> constructor  = null;
        try
        {
            constructor             = clazz.getConstructor();
        }
        catch (NoSuchMethodException e)
        {
            System.err.println("Can't get constructor!");
            e.printStackTrace();
        }

        try  { entity          = (T) constructor.newInstance();}
        catch (Exception e)
        {
            System.err.println("Can't use constructor!");
            e.getCause(); e.printStackTrace();
            return null;
        }

        if (entity == null)
        {
            System.err.println("Can't create new instance for " + clazz);
            return null;
        }

        return entity;
    }
}
