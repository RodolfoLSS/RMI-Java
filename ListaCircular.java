/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vinicius
 */
public class ListaCircular {
    
    private No no;
    private int contador;
    
    public ListaCircular() {
           this.no = null;
           contador = 0;
    }
    
    public int quantDeNo() {
            return contador;
        }

    public No getNo() {
        return no;
    }
        
    public void proximoNo() {
            no = no.getProximo();
    }
    
    public void add(No node) {
            if (this.no == null) {
                node.setProximo(node);
                this.no = node;
            } else {
                node.setProximo(this.no.getProximo());
                this.no.setProximo(node);
            }
            contador++;
        }
    
    public void delete(No node){
        
        /*No no1 = getNo();
        No no2 = getNo();
        
        if(no1.getId() == node.getId()){
            
            this.no = no1.getProximo();
        }else{
                
            while(no1.getId() != node.getId()){
                
                no2 = getNo();
                proximoNo();
                no1 = getNo();
            }
        
            no2.setProximo(no1.getProximo());
        }*/
        
        No no1 = getNo();
        
        while(no1.getId() != node.getId()){
                
                proximoNo();
                no1 = getNo();
            }
        
            no1.setId(-1);
        
        contador--;
        
    }
}
