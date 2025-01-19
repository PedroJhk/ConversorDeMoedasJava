import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String busca = "";
        Scanner leitura = new Scanner(System.in);


            // URL da API de câmbio
            String url = "https://v6.exchangerate-api.com/v6/55fa97213f1686e6360dde6e/latest/USD";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Convertendo a resposta JSON em um objeto JSONObject
            JSONObject jsonResponse = new JSONObject(response.body());

            // Extraindo valores das moedas
            double brlRate = jsonResponse.getJSONObject("conversion_rates").getDouble("BRL"); // Real Brasileiro
            double eurRate = jsonResponse.getJSONObject("conversion_rates").getDouble("EUR"); // Euro
            double arsRate = jsonResponse.getJSONObject("conversion_rates").getDouble("ARS"); // Peso Argentino
            double copRate = jsonResponse.getJSONObject("conversion_rates").getDouble("COP"); // Peso Colombiano

            int opcao = 0;

            while (opcao != 7) {
                System.out.println("""
                        O que deseja converter?
                        
                        1- Dólar =>> Peso Argentino
                        2- Peso Argentino =>> Dólar
                        3- Dólar =>> Real Brasileiro
                        4- Real Brasileiro =>> Dólar
                        5- Dólar =>> Peso Colombiano
                        6- Peso Colombiano =>> Dólar
                        7- Sair
                        """);

                System.out.print("Escolha uma opção: ");
                opcao = leitura.nextInt();

                if (opcao == 7) {
                    System.out.println("Saindo...");
                    break;
                }

                System.out.print("Digite o valor para converter: ");
                double valor = leitura.nextDouble();
                double resultado = 0;

                switch (opcao) {
                    case 1 -> resultado = valor * arsRate;  // Dólar para Peso Argentino
                    case 2 -> resultado = valor / arsRate;  // Peso Argentino para Dólar
                    case 3 -> resultado = valor * brlRate;  // Dólar para Real Brasileiro
                    case 4 -> resultado = valor / brlRate;  // Real Brasileiro para Dólar
                    case 5 -> resultado = valor * copRate;  // Dólar para Peso Colombiano
                    case 6 -> resultado = valor / copRate;  // Peso Colombiano para Dólar
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }

                if (opcao >= 1 && opcao <= 6) {
                    System.out.printf("O valor convertido é: %.2f%n", resultado);
                }
            }
        }


    }

