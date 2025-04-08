package edu.uncc.assessment04;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.assessment04.fragments.giftlists.AddItemToGiftListFragment;
import edu.uncc.assessment04.fragments.users.AddUserFragment;
import edu.uncc.assessment04.fragments.giftlists.CreateNewGiftListFragment;
import edu.uncc.assessment04.fragments.users.PassCodeFragment;
import edu.uncc.assessment04.fragments.giftlists.GiftListDetailsFragment;
import edu.uncc.assessment04.fragments.giftlists.GiftListsFragment;
import edu.uncc.assessment04.fragments.users.UsersFragment;
import edu.uncc.assessment04.models.GiftList;
import edu.uncc.assessment04.models.GiftListItem;
import edu.uncc.assessment04.models.User;

public class MainActivity extends AppCompatActivity implements UsersFragment.UsersListener, AddUserFragment.AddUserListener,
        PassCodeFragment.PassCodeListener, GiftListsFragment.GiftListsListener, GiftListDetailsFragment.GiftListDetailsListener,
        CreateNewGiftListFragment.CreateNewGiftListListener, AddItemToGiftListFragment.AddItemToGiftListListener {

    AppDatabase db;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        if(currentUser == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new UsersFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new GiftListsFragment())
                    .commit();
        }
    }

    //UsersFragment.UsersListener
    @Override
    public void gotoEnterPassCode(User user) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, PassCodeFragment.newInstance(user))
                .commit();
    }

    //UsersFragment.UsersListener
    @Override
    public void gotoAddUser() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new AddUserFragment())
                .commit();
    }

    //UsersFragment.UsersListener
    @Override
    public ArrayList<User> getAllUsers() {
        //TODO: Fetch all the users from the database
        List<User> users = db.userDao().getAll();
        if(users != null){
            return new ArrayList<>(users);
        }

        return new ArrayList<User>();
    }

    //AddUserFragment.AddUserListener
    @Override
    public void saveNewUser(User user) {
        //TODO: save the new user to the database
        db.userDao().insertALL(user);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //AddUserFragment.AddUserListener
    @Override
    public void cancelAddUser() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //PassCodeFragment.PassCodeListener
    @Override
    public void onPasscodeSuccessful(User user) {
        currentUser = user;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new GiftListsFragment())
                .commit();
    }

    //PassCodeFragment.PassCodeListener
    @Override
    public void onPassCodeCancel() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //GiftListsFragment.GiftListsListener
    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    //GiftListsFragment.GiftListsListener
    @Override
    public void gotoAddNewGiftList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new CreateNewGiftListFragment())
                .commit();
    }

    //GiftListsFragment.GiftListsListener
    @Override
    public void logout() {
        currentUser = null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment())
                .commit();
    }

    //GiftListsFragment.GiftListsListener
    @Override
    public void gotoListDetails(GiftList toDoList) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, GiftListDetailsFragment.newInstance(toDoList))
                .commit();
    }

    //GiftListsFragment.GiftListsListener
    @Override
    public ArrayList<GiftList> getAllGiftListsForUser(User user) {
        //TODO: Fetch the GiftLists from the database for the given user
        List<GiftList> giftLists = db.giftListDao().getAllGiftListsForUser(user.getId());
        if(giftLists != null){
            return new ArrayList<>(giftLists);
        }
        return new ArrayList<>();
    }

    //GiftListsFragment.GiftListsListener
    @Override
    public void deleteGiftList(GiftList giftList) {
        //TODO: Delete the GiftList from the database
        db.giftListDao().delete(giftList);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new GiftListsFragment())
                .commit();


    }

    //GiftListDetailsFragment.GiftListDetailsListener
    @Override
    public void gotoAddGiftListItem(GiftList giftList) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, AddItemToGiftListFragment.newInstance(giftList))
                .commit();
    }

    //GiftListDetailsFragment.GiftListDetailsListener
    @Override
    public ArrayList<GiftListItem> getAllItemsForGiftList(GiftList giftList) {
        //TODO: Fetch the GiftListItems from the database for the given ToDoList
        List<GiftListItem> giftListItems = db.giftListItemDao().getAllItemsForGiftList(giftList.getGiftId());
        if (giftListItems != null) {
            return new ArrayList<>(giftListItems);
        }

        return new ArrayList<>();    }

    //GiftListDetailsFragment.GiftListDetailsListener
    @Override
    public void goBackToGiftLists() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new GiftListsFragment())
                .commit();
    }

    //GiftListDetailsFragment.GiftListDetailsListener
    @Override
    public void deleteGiftListItem(GiftList giftList, GiftListItem giftListItem) {
        //TODO: Delete the GiftListItem from the database
        db.giftListItemDao().deleteGiftListItem(giftListItem);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, GiftListDetailsFragment.newInstance(giftList))
                .commit();
    }

    //CreateNewGiftListFragment.CreateNewGiftListListener
    @Override
    public void onCreateNewGiftList(User user, String listName) {
        //TODO: Save the new GiftList to the database
        GiftList giftList = new GiftList(listName, user.getId());
        db.giftListDao().insert(giftList);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new GiftListsFragment())
                .commit();
    }

    //CreateNewGiftListFragment.CreateNewGiftListListener
    @Override
    public void onCancelCreateNewGiftList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new GiftListsFragment())
                .commit();
    }

    //AddItemToGiftListFragment.AddItemToGiftListListener
    @Override
    public void onAddItemToGiftList(User user, GiftList giftList, String itemName, Integer quantity) {
        //TODO: Save the new GiftListItem to the database
        GiftListItem giftListItem = new GiftListItem(itemName, quantity, giftList.getGiftId(), user.getId());
        db.giftListItemDao().insertGiftListItem(giftListItem);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, GiftListDetailsFragment.newInstance(giftList))
                .commit();
    }

    //AddItemToGiftListFragment.AddItemToGiftListListener
    @Override
    public void onCancelAddItemToGiftList(GiftList giftList) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, GiftListDetailsFragment.newInstance(giftList))
                .commit();
    }
}

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User findById(long id);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);


    @Update
    void update(User user);

    @Insert
    void insertALL(User... users);

    @Delete
    void delete(User user);
}

@Dao
interface GiftListDao {
    @Query("SELECT * FROM giftLists WHERE userId = :userId")
    List<GiftList> getAllGiftListsForUser(long userId);

    @Insert
    void insert(GiftList giftList);

    @Insert
    void insertAll(GiftList... giftLists);

    @Update
    void update(GiftList giftList);

    @Delete
    void delete(GiftList giftList);
}

@Dao
interface GiftListItemDao {
    @Query("SELECT * FROM giftLists WHERE userId = :userId")
    List<GiftList> getAllGiftListsForUser(long userId);

    @Query("SELECT * FROM giftListItems WHERE giftId = :giftId")
    List<GiftListItem> getAllItemsForGiftList(long giftId);


    @Insert
    void insert(GiftList giftList);

    @Insert
    void insertAll(GiftList... giftLists);

    @Update
    void update(GiftList giftList);

    @Delete
    void delete(GiftList giftList);

    @Insert
    void insertGiftListItem(GiftListItem giftListItem);

    @Delete
    void deleteGiftListItem(GiftListItem giftListItem);

}

@Database(entities = {User.class, GiftList.class, GiftListItem.class}, version = 3)
abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract GiftListDao giftListDao();
    public abstract GiftListItemDao giftListItemDao();
}
