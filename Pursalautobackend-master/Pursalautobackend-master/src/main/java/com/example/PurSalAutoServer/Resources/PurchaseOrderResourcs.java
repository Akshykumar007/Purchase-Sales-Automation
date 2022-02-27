package com.example.PurSalAutoServer.Resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.CompanyBean;
import com.example.PurSalAutoServer.Bean.DistributorBean;
import com.example.PurSalAutoServer.Bean.PurchaseStockBean;
import com.example.PurSalAutoServer.Bean.StockMailStatusBean;
import com.example.PurSalAutoServer.Entities.Company;
import com.example.PurSalAutoServer.Entities.Distributor;
import com.example.PurSalAutoServer.Entities.POIndicator;
import com.example.PurSalAutoServer.Entities.PurchaseOrder;
import com.example.PurSalAutoServer.Entities.PurchaseStock;
import com.example.PurSalAutoServer.Entities.StockMailStatus;

@Path("po")
public class PurchaseOrderResourcs {
	@Path("ackpo")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getpo(POIndicator indicator) {
		PurchaseOrder toreturn = new PurchaseOrder();
		try {
			DistributorBean disbean = new DistributorBean();
			CompanyBean compbean = new CompanyBean();
			PurchaseStockBean stockbean = new PurchaseStockBean();
			StockMailStatusBean mailbean = new StockMailStatusBean();
			
			Company company = compbean.getcompanydetailswithuid("" + indicator.getUid());
			PurchaseStock stock = stockbean.getstock(indicator.getUid(), indicator.getStockid());
			StockMailStatus status = mailbean.getstockmailstatus("" + indicator.getUid(),stock.getName());
			Distributor dis = disbean.getdistributor("" + indicator.getUid(), status.getDistributor());
			
			toreturn.setCompany(company);
			toreturn.setStock(stock);
			toreturn.setDistributor(dis);
			toreturn.setStockmailstatus(status);
			return Response.ok(toreturn, MediaType.APPLICATION_JSON).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot retrive company details :(").build();
		}
	}
	
	@AuthNeeded
	@Path("viewpo")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getpo(@HeaderParam("uid") String uid,POIndicator indicator) {
		PurchaseOrder toreturn = new PurchaseOrder();
		try {
			DistributorBean disbean = new DistributorBean();
			CompanyBean compbean = new CompanyBean();
			PurchaseStockBean stockbean = new PurchaseStockBean();
			StockMailStatusBean mailbean = new StockMailStatusBean();
			
			Company company = compbean.getcompanydetailswithuid(uid);
			PurchaseStock stock = stockbean.getstock(Integer.parseInt(uid), indicator.getStockid());
			StockMailStatus status = mailbean.getstockmailstatus(uid,stock.getName());
			Distributor dis = disbean.getdistributor(uid, status.getDistributor());
			
			toreturn.setCompany(company);
			toreturn.setStock(stock);
			toreturn.setDistributor(dis);
			toreturn.setStockmailstatus(status);
			return Response.ok(toreturn, MediaType.APPLICATION_JSON).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot retrive company details :(").build();
		}
	}
}
