# AEDS II - Medalhistas

Este é um projeto desenvolvido para a disciplina de **Algoritmos e Estruturas de Dados II** (AEDS II). O objetivo do projeto é gerenciar informações sobre medalhistas dos Jogos Olímpicos de Paris 2024, incluindo a coleta de dados sobre suas medalhas e a geração de relatórios.

## Estrutura do Projeto

O projeto é composto por várias classes em Java, organizadas conforme a estrutura de um sistema que gerencia medalhistas e suas respectivas medalhas.

### Classes Principais

- **Medalhista**: Representa um atleta medalhista, armazenando informações como nome, país, data de nascimento e as medalhas conquistadas.
- **Medalha**: Representa uma medalha obtida por um atleta, com informações sobre a disciplina, o evento, a data e o tipo da medalha (ouro, prata ou bronze).
- **TipoMedalha**: Um enumerador que define os três tipos de medalhas: OURO, PRATA e BRONZE.

### Funcionalidades

- **Cadastro de Medalhas**: Adiciona medalhas para um atleta no sistema.
- **Relatórios**: Gera relatórios sobre as medalhas de um atleta, filtrando por tipo de medalha.
- **Leitura de Arquivo CSV**: Importa informações de medalhistas e suas medalhas de um arquivo CSV.

## Como Executar

1. Clone o repositório:
    ```bash
    git clone https://github.com/arthurcuri/aeds-medallist.git
    ```

2. Compile os arquivos Java:
    ```bash
    javac src/*.java
    ```

3. Execute o programa:
    ```bash
    java src.Aplicacao
    ```

4. O programa solicitará a entrada de dados para gerar relatórios. Digite o nome do atleta e o tipo da medalha para obter o relatório das medalhas conquistadas.

## Exemplo de Arquivo CSV

O arquivo CSV usado para carregar os dados dos medalhistas deve ter o seguinte formato:

```csv
name,medal_type,medal_date,gender,birth_date,country,discipline,event
EVENEPOEL Remco,OURO,2024-07-27,MASCULINO,2000-01-25,Belgium,Cycling Road,Men's Individual Time Trial
GANNA Filippo,PRATA,2024-07-27,MASCULINO,1996-07-25,Italy,Cycling Road,Men's Individual Time Trial
