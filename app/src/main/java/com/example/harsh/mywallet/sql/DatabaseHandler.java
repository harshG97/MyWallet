package com.example.harsh.mywallet.sql;

/**
 * Created by Harsh on 17-01-2017.
 */


    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import android.content.ContentValues;
    import android.content.Context;
    import android.content.Intent;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    import com.example.harsh.mywallet.models.ExpenseDetail;
    import com.example.harsh.mywallet.models.Lending;

public class DatabaseHandler extends SQLiteOpenHelper {

        // All Static variables
        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "expenseManager";

        // Contacts table name
        private static final String TABLE_EXPENSES = "expenses";
        private static  final  String TABLE_LENDINGS = "lendings";

        // Contacts Table Columns names
        private static final String KEY_DAY = "day";
        private static final String KEY_MONTH = "month";
        private static final String KEY_YEAR = "year";
        private static final String KEY_CATEGORY = "category";
        private static final String KEY_DETAILS = "details";
        private static final String KEY_AMOUNT = "amount";

        private static final String KEY_ID = "id";
        private static final String KEY_TO = "lentto";
        private static final String KEY_PAID = "paid";

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EXPENSES + " ( _Id INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + KEY_DAY + " INTEGER , " + KEY_MONTH + " INTEGER , "
                    + KEY_YEAR + " INTEGER , " + KEY_CATEGORY + " TEXT ," + KEY_DETAILS + " TEXT , " + KEY_AMOUNT + " REAL " + ")";
            db.execSQL( "CREATE TABLE expenses ( day INTEGER, month INTEGER, year INTEGER, category TEXT, details TEXT, amount REAL )");
            db.execSQL(" create table lendings ( id INTEGER PRIMARY KEY AUTOINCREMENT, day INTEGER, month INTEGER, year INTEGER, lentto TEXT, amount REAL, paid REAL )");


        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LENDINGS);

            // Create tables again
            onCreate(db);
        }

        /**
         * All CRUD(Create, Read, Update, Delete) Operations
         */

        // Adding new expense
        public void addExpense(ExpenseDetail expense) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_DAY, expense.getDay()); // Contact Name
            values.put(KEY_MONTH, expense.getMonth()); // Contact Phone
            values.put(KEY_YEAR, expense.getYear());
            values.put(KEY_CATEGORY, expense.getCategory());
            values.put(KEY_DETAILS, expense.getDetails());
            values.put(KEY_AMOUNT, expense.getAmount());


            // Inserting Row
            db.insert(TABLE_EXPENSES, null, values);
            db.close(); // Closing database connection
        }

        public void addLending(Lending l){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_DAY, l.getDay()); // Contact Name
            values.put(KEY_MONTH, l.getMonth()); // Contact Phone
            values.put(KEY_YEAR, l.getYear());
            values.put(KEY_TO,l.getTo());
            values.put(KEY_AMOUNT,l.getAmount());
            values.put(KEY_PAID,l.getPaid());

            db.insert(TABLE_LENDINGS, null, values);
            db.close();

        }

        public List<Lending> getLendings() {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from lendings ", null);

            List<Lending> lendList = new ArrayList<>();
            Lending l;
            int i = 0;
            if (cursor != null) {
                cursor.moveToFirst();

                while (i < cursor.getCount()) {
                    l = new Lending(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),
                            Integer.parseInt(cursor.getString(3)), cursor.getString(4), Double.parseDouble(cursor.getString(5)), Double.parseDouble(cursor.getString(6)));

                    lendList.add(l);
                    i++;
                    cursor.moveToNext();
                }
                cursor.close();
                }

            return lendList;
            }

        public List<ExpenseDetail> getExpense(int day, int month, int year) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_EXPENSES, new String[] { KEY_CATEGORY,
                            KEY_DETAILS, KEY_AMOUNT }, KEY_DAY + " = "   + Integer.toString(day)   +
                    " AND " + KEY_MONTH + " = "   + Integer.toString(month)    +
                    " AND " + KEY_YEAR + " = "   + Integer.toString(year)   ,
                      null, null, null, null);

            List<ExpenseDetail> expense = new ArrayList<>();
            int i = 0;
            if (cursor != null){
                cursor.moveToFirst();

                while(i<cursor.getCount()) {
                    ExpenseDetail e = new ExpenseDetail(day, month, year, cursor.getString(0),
                            cursor.getString(1), Double.parseDouble(cursor.getString(2)));
                    expense.add(e);
                    i++;
                    cursor.moveToNext();
                }
                cursor.close();
            }



            return expense;
        }

    public HashMap getPie(int day1, int month1, int year1, int day2, int month2, int year2) {
        SQLiteDatabase db = this.getReadableDatabase();
        String where;

        if(month1 == month2 && year1 == year2){
            where = "month = "+ Integer.toString(month1) + " and year = " + Integer.toString(year1)+ " and ( day between " + Integer.toString(day1)+ " and "+ Integer.toString(day2)+ ")";

        }
        else
        if(year1 == year2)
           where =  " (month > " + Integer.toString(month1) + " and year = " + Integer.toString(year1)+ " and month < " + Integer.toString(month2)+ "  )" +
                    " or ( month = " +Integer.toString(month1)+ " and (day between " + Integer.toString(day1)+ " and " +Integer.toString(31) + ") and (year = " + Integer.toString(year1)+ " ) ) " +
                    " or ( month = " + Integer.toString(month2) + " and " + "(day between "+Integer.toString(1)  +
                    " and " +  Integer.toString(day2)+ " ) and (year = " + Integer.toString(year2) + ") ) " ;



        else
        where = " (month > " + Integer.toString(month1) + " and year = " + Integer.toString(year1)+ "  ) or ( month < "+ Integer.toString(month2)+ " and year = " + Integer.toString(year2)+ " ) " +
                " or ( month = " +Integer.toString(month1)+ " and (day between " + Integer.toString(day1)+ " and " +Integer.toString(31) + ") and (year = " + Integer.toString(year1)+ " ) ) " +
                " or ( month = " + Integer.toString(month2) + " and " + "(day between "+Integer.toString(1)  +
                " and " +  Integer.toString(day2)+ " ) and (year = " + Integer.toString(year2) + ") ) or ( year > "+ Integer.toString(year1)+ " and "+" year < "+ Integer.toString(year2)+" )" ;



        Cursor cursor = db.query(TABLE_EXPENSES,
                new String[] { KEY_CATEGORY, "SUM("+KEY_AMOUNT+")" }, where, null, KEY_CATEGORY , null, null);


        HashMap<String, String> sumAmount = new HashMap<>();
        sumAmount.put("Food",Float.toString(0f));
        sumAmount.put("Entertainment",Float.toString(0f));
        sumAmount.put("Transport",Float.toString(0f));
        sumAmount.put("Medical",Float.toString(0f));
        sumAmount.put("Bills",Float.toString(0f));
        sumAmount.put("Social",Float.toString(0f));
        sumAmount.put("Education",Float.toString(0f));
        sumAmount.put("Other",Float.toString(0f));

        Log.i("sql",sumAmount.toString());




        int i = 0;
        if (cursor != null){
            cursor.moveToFirst();

            while(i<cursor.getCount()) {
                if(Double.parseDouble(cursor.getString(1))>0) {
                    sumAmount.remove(cursor.getString(0));
                    sumAmount.put(cursor.getString(0),cursor.getString(1));
                }



                i++;
                cursor.moveToNext();
            }
            cursor.close();
        }


        Log.i("sql",sumAmount.toString());

        return sumAmount;
    }

    public ArrayList<String> getbar ( int month2, int year2) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSES,
                new String[] { "day", "SUM("+KEY_AMOUNT+")" },
                KEY_MONTH + " = " + Integer.toString(month2) + " and "+ "year = " + Integer.toString(year2),

                       // " between "   + Integer.toString(month1)  + " and " + Integer.toString(month2),
                null, "day" , null, null);


        ArrayList<String> sumAmount = new ArrayList<>();
        int j =0;
        while(j<31){
            sumAmount.add(Float.toString(0f));
            j++;
        }


        Log.i("sql1",sumAmount.toString());




        int i = 0;
        if (cursor != null){
            cursor.moveToFirst();

            while(i<cursor.getCount()) {


                if(Double.parseDouble(cursor.getString(1))>0) {

                    sumAmount.remove(Integer.parseInt(cursor.getString(0))-1);
                    sumAmount.add(Integer.parseInt(cursor.getString(0))-1, cursor.getString(1));
                }








                i++;
                cursor.moveToNext();
            }
            cursor.close();
        }


        Log.i("sql2",sumAmount.toString());

        return sumAmount;
    }


    public ArrayList<String> getMonthlyBar( int year2) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSES,
                new String[] { "month", "SUM("+KEY_AMOUNT+")" },

             //   "( " + KEY_DAY + "> "   + Integer.toString(1)  +" )"

              //          +
                KEY_YEAR + " = "+ Integer.toString(year2) ,
                    //    "( " + KEY_YEAR + " between "   + Integer.toString(year2-1)  + " and " + Integer.toString(year2) + " )"
                    //    +
                    //    " and " + "( NOT ( " + "month = " + Integer.toString(month2) + " and " + "year = " + Integer.toString(year2-1)+ " ) )",
                null, "month" , null, null);


        ArrayList<String> sumAmount = new ArrayList<>();
        int j =0;
        while(j<12){
            sumAmount.add(Float.toString(0f));
            j++;
        }


        Log.i("sql",sumAmount.toString());




        int i = 0;
        if (cursor != null){
            cursor.moveToFirst();

            while(i<cursor.getCount()) {


                if(Double.parseDouble(cursor.getString(1))>0) {

                    sumAmount.remove(Integer.parseInt(cursor.getString(0))-1);
                    sumAmount.add(Integer.parseInt(cursor.getString(0))-1, cursor.getString(1));



                   /* if(Integer.parseInt(cursor.getString(0))<= month2) {
                        sumAmount.remove(12 + Integer.parseInt(cursor.getString(0)) - month2 - 1);
                        sumAmount.add(12 + Integer.parseInt(cursor.getString(0)) - month2 - 1, cursor.getString(1));
                    }

                    else {
                        sumAmount.remove( Integer.parseInt(cursor.getString(0)) - month2-1);
                        sumAmount.add(Integer.parseInt(cursor.getString(0)) - month2-1, cursor.getString(1));
                    }
                    */

                }








                i++;
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sumAmount;

    }

    public Double getThisMonth(int month, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        double amt = 0;

        Cursor cursor = db.query(TABLE_EXPENSES,
                new String[]{KEY_AMOUNT},
                KEY_MONTH + " = " + Integer.toString(month) + " and " + "year = " + Integer.toString(year),

                // " between "   + Integer.toString(month1)  + " and " + Integer.toString(month2),
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int i = 0;
            while (i < cursor.getCount()) {

                //   Log.i("amt", Double.toString(Double.parseDouble(cursor.getString(0))));

                amt += Double.parseDouble(cursor.getString(0));
                cursor.moveToNext();
                ++i;
            }
            cursor.close();
        }


            return amt;


    }

    /*
        // Getting All Contacts
        public List<Contact> getAllContacts() {
            List<Contact> contactList = new ArrayList<Contact>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            return contactList;
        }

        // Updating single contact
        public int updateContact(Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getPhoneNumber());

            // updating row
            return db.update(TABLE_EXPENSES, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(contact.getID()) });
        }

        // Deleting single contact
        public void deleteContact(Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
                    new String[] { String.valueOf(contact.getID()) });
            db.close();
        }


        // Getting contacts Count
        public int getContactsCount() {
            String countQuery = "SELECT  * FROM " + TABLE_EXPENSES;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }

    }

    */
}
