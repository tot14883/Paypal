package test.palpay;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by User on 1/6/2018.
 */

public class Cart {
   Map<Product,Integer> m_cart;
   double m_value = 0;
   Cart(){
       m_cart = new LinkedHashMap<>();
   }
   void addToCart(Product product){
       if(m_cart.containsKey(product))
           m_cart.put(product,m_cart.get(product)+1);
       else
           m_cart.put(product,1);

       m_value += product.getValue();
   }
   int getQuantity(Product product){
       return  m_cart.get(product);
   }
   Set getProduct(){
       return m_cart.keySet();
   }
   void empty(){
       m_cart.clear();
       m_value = 0;
   }
   double getValue(){
       return m_value;
   }
   int getSize(){
       return m_cart.size();
   }
}
