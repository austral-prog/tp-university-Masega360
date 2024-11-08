package com.university.model.cli.repository;

import com.university.model.cli.CLI;
import com.university.model.cli.CLIImpl;

import java.util.Scanner;

public class CLIApp {
    private Scanner scanner;
    private CriteriaRepository criteriaRepository;
    private StudentRepository studentRepository;
    private EvaluationRepository evaluationRepository;
    private CRUDRepository<?>[] crudRepositories;

    public CLIApp() {
        this.criteriaRepository = new CriteriaRepository();
        this.studentRepository = new StudentRepository();
        this.evaluationRepository = new EvaluationRepository();
        this.crudRepositories = new CRUDRepository<?>[]{
                studentRepository,
                evaluationRepository,
                criteriaRepository
        };
    }

    public void initializeRepositories() {
        criteriaRepository.CriteriaRepositoryInitializer();
        studentRepository.StudentRepositoryInitializer();
        evaluationRepository.EvaluationRepositoryInitializer();
    }

    public CRUDRepository<?>[] getCrudRepositories() {
        return crudRepositories;
    }

    public static void main(String[] args) {
        CLIApp app = new CLIApp(); // Instantiate CLIApp to initialize repositories
        app.initializeRepositories(); // Initialize each repository

        CLIImpl cli = new CLIImpl();
        cli.runCLI(app.getCrudRepositories()); // Run CLI with the initialized repositories
    }
}
