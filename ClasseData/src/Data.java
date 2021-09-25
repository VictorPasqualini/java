public class Data{
    int componentes[];
    static char separador = '/';
    static int formato = 0;
    static int termos[] = {0, 1, 2};
        
    public boolean bissexto(){
        return (((componentes[2] % 4 == 0) && (componentes[2] % 100 != 0)) || (componentes[2] % 400 == 0)); 
    }

    static boolean bissexto(int ano) {
        return (((ano % 4 == 0) && (ano % 100 != 0)) || (ano % 400 == 0));
    }
    
    static int diasMes(int mes, int ano) {
        int dias;

        switch(mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dias = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                dias = 30;    
                break;
            case 2:
                dias = (bissexto(ano)) ? 29 : 28;
            default:
                dias = 0; 
        }
        return dias;
    }

    public boolean consistencia(){
        boolean ret = true;

        if(componentes[1] < 1 || componentes[1] > 12){
            ret = false;
        }
        else if(componentes[0] < 1 || componentes[0] > diasMes(componentes[1], componentes[2])){
            ret = false;
        }

        return ret;
    }

    static boolean consistencia(int dia, int mes, int ano){
        boolean ret = true;

        if(mes < 1 || mes > 12){
            ret = false;
        }
        else if(dia < 1 || dia > diasMes(mes, ano)){
            ret = false;
        }

        return ret;
    }

    public String dataString() {
        return "" + componentes[termos[0]]
                    + separador
                    + componentes[termos[1]]
                    + separador
                    + componentes[termos[2]];
    }

    public boolean stringData(String s){
        int pos1 = 0, pos2 = 0;
        String sub1, sub2, sub3;

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == separador){
                pos1 = i;
                break;
            }
        }

        for(int i = (pos1 + 1); i < s.length(); i++){
            if(s.charAt(i) == separador){
                pos2 = i;
                break;
            }
        }

        sub1 = s.substring(0, pos1);
        sub2 = s.substring(pos1+1, pos2);
        sub3 = s.substring(pos2+1);

        componentes[termos[0]] = Integer.parseInt(sub1);
        componentes[termos[1]] = Integer.parseInt(sub2);
        componentes[termos[2]] = Integer.parseInt(sub3);

        return consistencia();
    }

    static boolean mudaFormato (int f) {
        boolean ret = true;
        formato = f;
        
        switch(formato){
            case 0:
                separador = '/';
                termos[0] = 0;
                termos[1] = 1;
                termos[2] = 2;
                break;
            case 1:
                separador = '/';
                termos[0] = 1;
                termos[1] = 0;
                termos[2] = 2;
                break;
            case 2:
                separador = '-';
                termos[0] = 0;
                termos[1] = 1;
                termos[2] = 2;
                break;
            case 3:
                separador = '.';
                termos[0] = 0;
                termos[1] = 1;
                termos[2] = 2;
                break;
            case 4:
                separador = '/';
                termos[0] = 0;
                termos[1] = 1;
                termos[2] = 2;
                break;
        }

        return ret;
    }
    
    public Data(int d, int m, int a){
        componentes[0] = d;
        componentes[1] = m;
        componentes[2] = a; 
        
        if(!consistencia()){
            componentes[0] = 0;
            componentes[1] = 0;
            componentes[2] = 0;
        }
    }

    public Data(String sd){
        if (!stringData(sd)){
            this.componentes[0] = 0;
            this.componentes[1] = 0;
            this.componentes[2] = 0;
        }
    }

    public Data(Data d) { 
        componentes[0] = d.componentes[0];
        componentes[1] = d.componentes[1];
        componentes[2] = d.componentes[2];
    }

    private int dataDias() {		
        int dias = 0, ctano = 1900, ctmes = 1;

        while(ctano < componentes[2]){
            dias += (bissexto(ctano)) ? 366 : 365;
            ctano++;
        }
        
        while(ctmes < componentes[1]) {
            dias += (diasMes(ctmes, ctano));
            ctmes++; 
        }
        
        dias += componentes[0];
        
        return dias;
    }

    private void diasData(int d) {
        int ctano = 1900, ctmes = 1, ctdias = 0, delta = 0;

        while(ctdias < d){
            delta = (bissexto(ctano)) ? 366 : 365;
            ctdias += delta;
            ctano++;
        }
        
        ctdias -= delta;
        componentes[2] = ctano;
        
        while(ctdias < d){
            delta = diasMes(ctmes, ctano);
            ctdias += delta;
            ctmes++;
        }

        ctdias -= delta;
        componentes[1] = ctmes;
        componentes[0] = d - ctdias;            
    }

    public Data somaDias(int d) {
        Data ret = new Data(1, 1, 1900);
        
        ret.diasData(dataDias() + d);
        
        return ret;
    }

    public Data subDias(int d) {
        Data ret = new Data(1, 1, 1900);
        
        ret.diasData(dataDias() - d);

        return ret;
    }

    public long subDias(Data d) {
        int dias = d.componentes[0], ctano = d.componentes[2], ctmes = d.componentes[1];

        while(ctano < componentes[2]){
            dias += (bissexto(ctano)) ? 366 : 365;
            ctano++;
        }
        
        while(ctmes < componentes[1]) {
            dias += (diasMes(ctmes, ctano));
            ctmes++; 
        }
        dias += componentes[0];

        Data ret = new Data(1, 1, 1900);

        return dias - ret.dataDias();
    }
}
