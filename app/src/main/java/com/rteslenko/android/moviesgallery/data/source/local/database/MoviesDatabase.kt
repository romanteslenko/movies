package com.rteslenko.android.moviesgallery.data.source.local.database

import androidx.room.*
import com.rteslenko.android.moviesgallery.data.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "title") val title: String
) {
    fun asDomainModel(): Movie {
        return Movie(id, overview, popularity, posterPath, releaseDate, title)
    }
}

fun List<MovieEntity>.asDomainModel() = map { movieEntity ->
    with(movieEntity) {
        movieEntity.asDomainModel()
    }
}

fun List<Movie>.asDbEntities() = map { movie ->
    with(movie) {
        MovieEntity(id, overview, popularity, posterPath, releaseDate, title)
    }
}

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieDetails(id: Long): MovieEntity?

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)
}

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}