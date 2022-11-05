package delegation;


import delegation.film.Film;

public class Main
{


    public static void main(String[] args)
    {
        Film a1 = new Film("1+1");
        a1.test(2);
        a1.setFilmType(Film.FilmType.REGULAR);
        a1.test(2);


        Film b2 = new Film("Spy Kids", Film.FilmType.NEW_FILM);
        b2.test(2);
        b2.setFilmType(Film.FilmType.CHILDREN);
        b2.test(2);
    }
}
