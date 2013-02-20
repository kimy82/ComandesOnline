package com.online.restfull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.online.restfull.exceptions.RestFullException;


@Path(value="/comandes")
public class ComandesManager {
	
	@javax.ws.rs.core.Context ServletContext context;
	
	
    public ComandesManager() {
    	
    }
   
    
    @GET
    @Produces(value="text/plain")
    public String getList(@QueryParam("a") String txt,@QueryParam("o") String orderNum) {
              
        if(txt!=null && orderNum!=null){
        	if(txt.equals("AC001")){
        		removeLineFromFile(this.context.getRealPath("/Downloads/comanda"+txt+".txt"),orderNum);
        	}
        }
        
        
        return "Line removed";
    }

    @GET
    @Produces(value="text/plain")
    @Path(value="/file")
    public String getPropety(@QueryParam("resid") String resId, 
    						@QueryParam("orderNum") String orderNum, 
    						@QueryParam("deliveryCharge") String deliveryCharge,
    						@QueryParam("total") String total,
    						@QueryParam("nom") String nom,
    						@QueryParam("address") String address,
    						@QueryParam("diahora") String diahora,
    						@QueryParam("telnumber") String telnumber,
    						@QueryParam("comanda") String comanda,
    						@QueryParam("comandaName") String comName,
    						@QueryParam("comandaHora") String comHora,
    						@QueryParam("comandaEntrega") String comEnt,
    						@QueryParam("comandaLimit") String comLim,
    						@QueryParam("pagada") String pagada,
    						@QueryParam("comment") String comments,
    						@QueryParam("nomRest") String nomRest,
    						@QueryParam("admin") String admin) {
        
    	if (resId!=null && !resId.equals("") && comanda!=null && !comanda.equals("")) {
    		
    		try {
    			
    			
				BufferedWriter writer = new BufferedWriter( new FileWriter( this.context.getRealPath("/Downloads/comanda"+resId+".txt") , true ) );							
				String comandaBuild = buildComanda(resId, orderNum, comanda, deliveryCharge,total,nom,
												   diahora, address,telnumber,comName,comHora,comEnt,comLim,
												   pagada,comments,nomRest, admin);
				writer.append(System.lineSeparator()+comandaBuild);
				writer.flush();
				writer.close();
				
    			
				
			} catch (IOException e) {
				return "No File problem"+e;
			}catch(Exception e){
				return "Error problem"+e;
			}
    		return "Do it";
        }
        else {
            return "Name of txt Not Found";
        }
    }
    
    //PRIVATES     #AC001*1*10005*1;Chiken;3.00;2;Beef;6.00;3;rice;2.50;*1.0*0;12.50;4;Tom;Address;15:47 03-08-10;113;7;cod:;008612345678;*Comment#0x0D0x0A


    
    public void removeLineFromFile(String file, String lineToRemove) throws RestFullException {

    	try {

    	  File inFile = new File(file);

    	  if (!inFile.isFile()) {
    	    System.out.println("Parameter is not an existing file");
    	    return;
    	  }

    	  //Construct the new file that will later be renamed to the original filename.
    	  File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

    	  BufferedReader br = new BufferedReader(new FileReader(file));
    	  PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

    	  String line = null;
    	  while ((line = br.readLine()) != null) {

    	    if (!line.trim().contains(lineToRemove)) {

    	      pw.println(line);
    	      pw.flush();
    	    }
    	  }
    	  pw.close();
    	  br.close();

    	  //Delete the original file
    	  if (!inFile.delete()) {    		  
    		  System.out.println("Could not delete file");
    		  throw new RestFullException("Could not delete file");
    	  }

    	  //Rename the new file to the filename the original file had.
    	  if (!tempFile.renameTo(inFile)){
    		  System.out.println("Could not rename file");
    		  throw new RestFullException("Could not rename file");
    	  }
    	    

    	}
    	catch (FileNotFoundException ex) {
    	  ex.printStackTrace();
    	  throw new RestFullException(ex,"Error deleting a line");
    	}
    	catch (IOException ex) {
    		ex.printStackTrace();
    		throw new RestFullException(ex,"Error deleting a line");
    	}
    }
    
    private String buildComanda(String resId, String orderNum,  String comanda, 
    							String deliveryCharge, String total, String nom,
    							String diahora,String address,String telnumber,
    							String comName, String comHora,String comEnt,
    							String comLim,String pagada, String comments, 
    							String nomRest, String admin) throws Exception {
    	try{
    		
    	
	    	StringBuffer comandaSB = new StringBuffer("#"+resId+"**"+orderNum+"*"+comanda);
	    	
	    	if(admin.equals("true")){
		    	comandaSB.append("**;"+total);
		    	comandaSB.append(";;"+"---------------------------;"+comName+"%%"+nom+"%%"+address+"%%"+comHora+"%%"+comEnt+
		    					 "%%"+comLim+"%%---------------------------%%"+pagada+"%%---------------------------%%"+telnumber+"%%---------------------------"+";;;");
		    	comandaSB.append(";;;*"+comments+"%%---------------------------#0x0D0x0A");
	    	}else{
	    		comandaSB.append("**;"+total);
		    	comandaSB.append(";;"+"---------------------------;"+nomRest+"%%"+comName+"%%"+nom+"%%"+comHora+"%%"+comEnt+"%%"+comLim+"%%---------------------------%%"+
		    					pagada+"%%---------------------------%%"+telnumber+"%%---------------------------%%"+"CHECKING/r A: %% R: %% E: %%;;;");
		    	comandaSB.append(";;;*"+comments+"%%---------------------------#0x0D0x0A");
	    	}
	    	
	    	return comandaSB.toString();
	    	
    	}catch(Exception e){
    		throw new Exception("wrong params");
    	}
    	
    }
   

}
