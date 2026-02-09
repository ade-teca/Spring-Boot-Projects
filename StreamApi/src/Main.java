import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Product> products = Arrays.asList(
                new Product("Mouse", 50.0, "Eletrônicos"),
                new Product("Teclado", 150.0, "Eletrônicos"),
                new Product("Monitor", 900.0, "Eletrônicos"),
                new Product("Cadeira", 1200.0, "Móveis"),
                new Product("Fone BT", 200.0, "Eletrônicos"),
                new Product("Smartphone", 2500.0, "Eletrônicos")
        );

//        O Desafio
//        Dada uma lista de produtos, você deve realizar as seguintes tarefas usando Stream API:
//        Filtrar apenas os produtos da categoria "Eletrônicos".
//        Ordenar os produtos pelo preço (do mais barato para o mais caro).
//        Transformar o resultado apenas nos nomes dos produtos.
//        Limitar o resultado aos 3 primeiros.

        List<String> newProducts = products.stream()
                .filter(product -> product.getCategory().equals("Eletrônicos"))
                .sorted(Comparator.comparingDouble(Product::getPrice)) // Ordenação
                .map(Product::getName) // Extrai apenas o nome (String)
                .collect(Collectors.toList());

        newProducts.forEach(System.out::println);


    }
}