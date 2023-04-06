package com.jdbc.starter;

import com.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class runner {
    public static void main(String[] args) throws SQLException {
        Scanner in = new Scanner(System.in);

        Class<Driver> driverClass = Driver.class;

        try (var connection = ConnectionManager.open();
             final var statement = connection.createStatement()) {
            int int_person_id=0;
        while (int_person_id==0) {
            System.out.print(" Login:");
            String login = in.nextLine();

            System.out.print(" password: ");
            int password = in.nextInt();

            ResultSet person_id = statement.executeQuery("SELECT MAX(id) FROM person WHERE login = '"+login+"'and password = '"+password+"'");

            person_id.next();
            int_person_id = person_id.getInt(1);

            if(int_person_id == 0)
            {
                System.out.print("Incorrect password. Try again.");
            }
            in.nextLine();
        }
            System.out.printf("\n  Your personal id %d : ", int_person_id);


            while (true)
        {


            System.out.print("\n 1 - display a table ");
            System.out.print("\n 2 - add a row (for admin)");
            System.out.print("\n 3 - delete a row (for admin)");
            System.out.print("\n 4 - add product in basket");
            System.out.print("\n 5 - EXIT ");
            System.out.print("\n Input a number: ");


            String numb = in.nextLine();

                switch (numb) {
                    case "1":

                        ResultSet resultSet = statement.executeQuery("SELECT * FROM product");

                        while (resultSet.next()) {
                            int id = resultSet.getInt(1);
                            String column_name = resultSet.getString(2);
                            int column_price = resultSet.getInt(3);
                            System.out.printf("%d. %s - %d \n", id, column_name, column_price);
                        }
                        break;
                    case "2":

                        System.out.print(" Input a name:");
                        in.nextLine();
                        String name = in.nextLine();
                        System.out.print(" Input a price: ");
                        int price = in.nextInt();

                        ResultSet max_id = statement.executeQuery("SELECT MAX(id) FROM product WHERE id is not null");
                        max_id.next();
                        int int_max_id = max_id.getInt(1) + 1;

                        int rows = statement.executeUpdate("INSERT INTO product VALUES (' " + int_max_id +"','"+name+"','"+price+"')");
                        break;

                    case "3":
                        System.out.print(" Input id: ");
                        int id_delete = in.nextInt();
                        int rows1 = statement.executeUpdate("DELETE FROM product WHERE id =' " + id_delete + "'" );
                        break;
                    case "4":
                        int int_id_product=0;
                        while (int_id_product==0) {
                        System.out.print(" Input name of product: ");

                        String name_product = in.nextLine();
                        ResultSet id_product = statement.executeQuery("SELECT MAX(id) FROM product WHERE name = '"+name_product+"'");
                        id_product.next();
                        int_id_product = id_product.getInt(1);
                        System.out.printf("\n Product id: %d",int_id_product);
                            if(int_id_product == 0)
                            {
                                System.out.print("Incorrect name of product. Try again.");
                            }
                            in.nextLine();
                        }
                        break;

                    case "5": return;
                    default: System.out.print("You are bastard. Try again.");
                    break;
                }
            }
        }




    }
}
