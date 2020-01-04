package com.ealmeida.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.ealmeida.livraria.modelo.Venda;

@Named
@ViewScoped // javax.faces.view.ViewScoped
public class VendasBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public BarChartModel getVendasModel() {

		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		model.setTitle("Vendas");
		model.setLegendPosition("ne");

		Axis xAxis = model.getAxis(AxisType.X);
		xAxis.setLabel("Título");

		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");

		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016");

		List<Venda> vendas = getVendas();

		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		model.addSeries(vendaSerie);

		return model;
	}

	public List<Venda> getVendas() {
		return entityManager.createQuery("select v from Venda v", Venda.class).getResultList();
	}
}
