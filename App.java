import java.io.Console;

class StringGuesser
{
    public static void main(String args[])
    {
        Console cls = System.console();

        System.out.print("Enter the target string: ");
        char target[] = cls.readLine().toCharArray();
        char chromosomes[] = new char[(26*2) + 10 + 4 + 1];
        for(byte a = 0; a < chromosomes.length / 2; ++a)
        {
            chromosomes[2*a] = (char)('a' + a);
            chromosomes[(2*a) + 1] = Character.toUpperCase((char)('a' + a));
        }
        for(byte b = 0; b < 11; ++b)
        {
            chromosomes[(26*2) + b] = (char)('0' + b);
        }
        chromosomes[62] = '.';
        chromosomes[63] = ',';
        chromosomes[64] = '?';
        chromosomes[65] = '!';
        chromosomes[chromosomes.length - 1] = ' ';
        
        GeneticModel gm = new GeneticModel(100, chromosomes, target.length);
        gm.StartIterations(target, 0.1, 0.5, 0.1);

    }
}