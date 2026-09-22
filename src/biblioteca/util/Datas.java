package biblioteca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Funções de apoio para trabalhar com datas no formato dd/MM/yyyy,
 * usadas nos cálculos de prazo/atraso e nos campos de data das telas.
 */
public class Datas {

    private static final SimpleDateFormat FORMATO_DATA = criarFormato();

    private static SimpleDateFormat criarFormato() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false); // recusa datas como 31/02/2026
        return formato;
    }

    public static String formatar(Date data) {
        return data != null ? FORMATO_DATA.format(data) : "";
    }

    // Retorna null quando o texto não é uma data válida.
    public static Date converter(String texto) {
        try {
            return FORMATO_DATA.parse(texto.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    // Zera hora/minuto/segundo, para comparar apenas o dia.
    public static Date inicioDoDia(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date somarDias(Date data, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inicioDoDia(data));
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }

    // Quantidade de dias corridos de "inicio" até "fim" (negativo se fim < inicio).
    public static long diasEntre(Date inicio, Date fim) {
        long diferenca = inicioDoDia(fim).getTime() - inicioDoDia(inicio).getTime();
        // Math.round compensa a hora a mais/a menos de dias com horário de verão.
        return Math.round(diferenca / (double) TimeUnit.DAYS.toMillis(1));
    }
}
