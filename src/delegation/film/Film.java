package delegation.film;
import delegation.film.general.IFilm;
import delegation.film.types.Children;
import delegation.film.types.NewFilm;
import delegation.film.types.Regular;

public class Film
{
    public enum FilmType
    {
        NEW_FILM(NewFilm.class),
        REGULAR(Regular.class),
        CHILDREN(Children.class);

        public <T extends IFilm> Class<T> cl()
        {
            return clazz;
        }
        Class clazz;
        <T extends IFilm> FilmType(Class<T> clazz)
        {
            this.clazz = clazz;
        }
    }

    private String _name;
    private IFilm _type;

    public Film(String name)
    {
        _name = name;
        _type = new NewFilm();
    }

    public Film(String name, FilmType filmType)
    {
        _name = name;
        _type = FilmTypeContext.instance().get(filmType.cl());
    }

    public double getPrice(int days)
    {
        return _type.getPrice(days);
    }


    public double getBonus(int days)
    {
        return _type.getBonus(days);
    }

    public void setFilmType(FilmType filmType)
    {
        _type = FilmTypeContext.instance().get(filmType.cl());
    }

    public void test(int days)
    {
        System.out.println(" ");
        System.out.println("FILM: " + _name + " for " +days+ " day/days");
        System.out.println("Film Type: " + _type.getClass().getSimpleName());
        System.out.println("price: " + _type.getPrice(days));
        System.out.println("Bonus that you get: " + _type.getBonus(days));
        System.out.println(" ");
    }
}
