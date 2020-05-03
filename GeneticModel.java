import java.util.InputMismatchException;
import java.util.Random;

public class GeneticModel 
{
    /*********************Instance Variables***************/ 
    private char [][] population;
    private char[] chromosomes;

    /**********************Constructors***********************/
    GeneticModel(int populationCount, char chromosomes[], int numChromosomes)
    {
        /*Initializes the genetic model.
        populationCount = the initial starting population
        chromosomes = array of chromosomes that an individual may contain
        numChromosomes = number of chromosomes each individual may contain
        */


        //Setting the model parameters
        this.chromosomes = chromosomes.clone(); //The chromosomes in the model

        //Generating an inital population of the given size
        GeneratePopulation(populationCount, numChromosomes);
    }

    /**********************Private Methods***********************/
    private void GeneratePopulation(int populationCount, int numChromosomes)
    {
        /*Generates a random population
        populationCount = the initial starting population
        numChromosomes = number of chromosomes each individual may contain
        */

        this.population = new char[populationCount][numChromosomes];
        for(int a = 0; a < populationCount; ++a)
        {
            //Populating the individual with random chromosomes
            for(int b = 0; b < numChromosomes; ++b)
            {
                this.population[a][b] = this.chromosomes[RandomInRange(0, this.chromosomes.length - 1)];
            }
        }

    }

    private int RandomInRange(int start, int end)
    {
        /* Returns a random number in the given range*/

        Random rand = new Random();

        return rand.nextInt((end - start) + 1) + start;
    }

    private double[] SortPopulation(char target[])
    {
        /*Sorts the population according to fitness and returns the fitness values*/

        //Calculating the fitness values for the population
        double fitness[] = new double[this.population.length]; //The fitness values of the population
        for(int a = 0; a < population.length; ++a)
        {
            fitness[a] = Fitness(population[a], target);
        }

        //Sorting the new population according to fitness
        SortByFitness(fitness, population);

        return fitness;
    }
    
    private double Fitness(char individual[], char target[])
    {
        return (double)CommonChromosomesCount(individual, target) / (double)target.length;
    }

    private int CommonChromosomesCount(char c1[], char c2[])
    {
        /*Returns the number of common chromosomes between two individuals*/

        int commons = 0; //The number of common chromosomes

        for(int a = 0;a < c1.length; ++a)
        {
            if(c1[a] == c2[a])
                ++commons;
        }

        return commons;

    }

    private void SortByFitness(double fitness[], char population[][])
    {
        for(int a = 0; a < fitness.length - 1; ++a)
        {
            boolean swapped = false;
            for(int b = 0; b < fitness.length - a - 1; ++b)
            {
                if(fitness[b] < fitness[b+1])
                {
                    //Swapping the fitness values
                    double temp = fitness[b];
                    fitness[b] = fitness[b+1];
                    fitness[b+1] = temp;

                    //Swapping the individuals
                    char temp_cr[] = population[b];
                    population[b] = population[b+1];
                    population[b+1] = temp_cr;

                    swapped = true;
                }
            }
            if(!swapped)
                break;
        }
    }

    private char[][] GetNewGeneration(double elitistRate, double matingSelectionRate, double mutationRate)
    {
        /*Returns the new generation*/

        //Initializing the next generation
        char newGen[][] = new char[population.length][population[0].length];

        //Adding the elitists to the next generation
        int elitists = (int)(elitistRate * population.length);
        for(int a = 0; a < elitists; ++a)
        {
            newGen[a] = population[a];
        }

        //Mating the population
        for(int a = 0; a < (population.length - elitists); ++a)
        {
            char parent1[] = population[RandomInRange(0, (int)(matingSelectionRate * population.length))];
            char parent2[] = population[RandomInRange(0, (int)(matingSelectionRate * population.length))];

            
            newGen[elitists + a] = CrossoverAndMutate(parent1, parent2, mutationRate);
        }

        return newGen;
    }

    private char[] CrossoverAndMutate(char parent1[], char parent2[], double mutationRate)
    {
        /*Performs crossover on the two parents*/

        char offspring[] = new char[parent1.length];

        for(int a = 0;a < offspring.length; ++a)
        {
            //Performing crossover
            double crossover_prob = Math.random();

            if(crossover_prob < (1.0 - mutationRate) / 2)
                offspring[a] = parent1[a];
            else if(crossover_prob < 1.0 - mutationRate)
                offspring[a] = parent2[a];
            else
                offspring[a] = chromosomes[RandomInRange(0, chromosomes.length - 1)];
        }
        
        return offspring;
    }

    /**********************Public Methods***********************/
    public void StartIterations(char target[], double elitistRate, double matingSelectionRate, double mutationRate)
    {
        double fitness[] = new double[population.length]; //The fitness values for the population
        int generationCtr = 1;

        do
        {
            //Sorting the population according to fitness
            fitness = SortPopulation(target);

            //Getting the next generation
            this.population = GetNewGeneration(elitistRate, matingSelectionRate, mutationRate).clone();

            System.out.println("Generation " + generationCtr++ + " : Max fitness = " + fitness[0] + ", Population size = " + population.length);

        }while(fitness[0] != 1);

        //Displaying the best solution
        System.out.print("\nBest solution = ");
        for(char c : population[0])
        {
            System.out.print(c);
        }  
    }


}