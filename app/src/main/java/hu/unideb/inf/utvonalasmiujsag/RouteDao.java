package hu.unideb.inf.utvonalasmiujsag;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RouteDao {
    @Insert
    void insertRoute(Route route);

    @Query("SELECT * FROM routes")
    List<Route> getAllRoutes();
}
