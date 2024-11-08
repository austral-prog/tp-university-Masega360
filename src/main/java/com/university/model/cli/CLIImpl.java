package com.university.model.cli;

import com.university.App;
import com.university.model.cli.repository.CRUDRepository;
import com.university.model.cli.repository.CriteriaRepository;
import com.university.model.cli.repository.EvaluationRepository;
import com.university.model.cli.repository.StudentRepository;
import com.university.model.student.Recordable;
import com.university.model.student.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLIImpl implements CLI {

    private static Scanner scanner;


    public CLIImpl() {
        this.scanner = new Scanner(System.in);

    }

    @Override
    public void runCLI(CRUDRepository<?>[] crudRepositories) {
        while (true) {
            System.out.println("Welcome to the University CLI!");
            System.out.println("Select an entity type to interact with:");

            // Display available entities
            for (int i = 0; i < crudRepositories.length; i++) {
                System.out.println((i + 1) + ". " + crudRepositories[i].getEntityClass().getSimpleName());
            }
            System.out.println("0. Exit");

            // Read user's choice
            int entityChoice = getChoice(crudRepositories.length);

            if (entityChoice == 0) {
                System.out.println("Exiting...");
                break;
            }

            CRUDRepository<?> selectedRepository = crudRepositories[entityChoice - 1];
            runEntityCRUDOperations((CRUDRepository<Entity>) selectedRepository);
        }
    }

    private void runEntityCRUDOperations(CRUDRepository<Entity> repository) {
        while (true) {
            System.out.println("\nSelect a CRUD operation for " + repository.getIdentifier() + ":");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Create New CSV");
            System.out.println("6. Search by Student Name");
            System.out.println("7. Create the original CSV");
            System.out.println("0. Go Back");

            int operationChoice = getChoice(6);
            if (operationChoice == 0) {
                break;
            }

            switch (operationChoice) {
                case 1:
                    performCreateOperation(repository);
                    break;
                case 2:
                    performReadOperation(repository);
                    break;
                case 3:
                    performUpdateOperation(repository);
                    break;
                case 4:
                    performDeleteOperation(repository);
                    break;
                case 5:
                    repository.writeCSV();;
                    break;
                case 6:
                    getByName(repository);;
                    break;
                case 7:
                    createOldCSV();
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }

    }
    private void createOldCSV(){
        App.coursesApp();
        App.examsApp();
        App.criteriaApp();
    }
    private void performCreateOperation (CRUDRepository <Entity> repository){
        System.out.println("Enter the details to create a new " + repository.getIdentifier() + ":");
        Entity entity = repository.constructFromList();
        repository.create(entity);
    }

    public static String userInput(String prompt) {
        System.out.println("Please enter " + prompt + ":");
        return scanner.nextLine();
    }
    public void getByName(CRUDRepository <Entity> repository){
        String name = userInput("Write the name of the Student: ");
        repository.showByStudentName(name);
    }


    private void performReadOperation(CRUDRepository<Entity> repository) {
        System.out.println("Enter the ID of the " + repository.getIdentifier() + " to read:");
        String id = scanner.nextLine();
        Integer idi = Integer.parseInt(id);
        Recordable entity = (Recordable) repository.read(idi);
        String readable = entity.toParsedString();
        System.out.println("Entity details: " + readable);
    }

    private void performUpdateOperation(CRUDRepository<Entity> repository) {
        System.out.println("Enter the ID of the " + repository.getEntityClass().getSimpleName() + " to update:");
        String id = scanner.nextLine();
        Integer idi = Integer.parseInt(id);
        System.out.println("Enter the new details for the entity:");
        Entity entity = repository.constructFromList();

        repository.update(idi, entity);
    }

    private void performDeleteOperation(CRUDRepository<?> repository) {
        System.out.println("Enter the ID of the " + repository.getEntityClass().getSimpleName() + " to delete:");
        String id = scanner.nextLine();
        Integer idi = Integer.parseInt(id);
        repository.delete(idi);
    }

    private int getChoice(int maxChoice) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 0 && choice <= maxChoice) {
                    return choice;
                } else {
                    System.out.println("Invalid choice, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
            }
        }
    }
}

