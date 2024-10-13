package hu.unideb.inf.utvonalasmiujsag;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Route.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RouteDao routeDao();
}