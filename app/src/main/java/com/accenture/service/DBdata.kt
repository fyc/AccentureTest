//package com.accenture.service
//
//import android.arch.persistence.room.ColumnInfo
//import android.arch.persistence.room.Dao
//import android.arch.persistence.room.Database
//import android.arch.persistence.room.Delete
//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.Insert
//import android.arch.persistence.room.OnConflictStrategy.REPLACE
//import android.arch.persistence.room.PrimaryKey
//import android.arch.persistence.room.Query
//import android.arch.persistence.room.RoomDatabase
//import android.arch.persistence.room.Update
//
//class DBdata {
//    @Entity(tableName = "task")
//    data class Task(@ColumnInfo(name = "completed_flag") var completed: Boolean = false,
//                    @ColumnInfo(name = "task_desciption") var description: String) {
//        @ColumnInfo(name = "id")
//        @PrimaryKey(autoGenerate = true) var id: Long = 0
//    }
//
//    @Dao
//    interface TaskDao {
//
//        @Query("select * from task")
//        fun getAllTasks(): List<Task>
//
//        @Query("select * from task where id = :p0")
//        fun findTaskById(id: Long): Task
//
//        @Insert(onConflict = REPLACE)
//        fun insertTask(task: Task)
//
//        @Update(onConflict = REPLACE)
//        fun updateTask(task: Task)
//
//        @Delete
//        fun deleteTask(task: Task)
//    }
//
//    @Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
//    abstract class AppDatabase : RoomDatabase() {
//
//        abstract fun taskDao(): TaskDao
//    }
//}