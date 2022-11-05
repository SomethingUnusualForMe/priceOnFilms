package delegation.film.general;

public abstract class AFilm implements IFilm
{
    final FilmInfo _info = getClass().getAnnotation(FilmInfo.class);
    public double getPrice(int days)
    {
        return days * _info.priceMod();
    }

    public double getBonus(int days)
    {
        return days >= 2 ? days * _info.bonusMod() : 0;
    }
}
