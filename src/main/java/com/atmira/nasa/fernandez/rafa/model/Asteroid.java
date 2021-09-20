package com.atmira.nasa.fernandez.rafa.model;

public class Asteroid implements Comparable<Asteroid> {

	private String nombre;

	private String diametro;

	private String velocidad;

	private String fecha;

	private String planeta;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDiametro() {
		return diametro;
	}

	public void setDiametro(String diametro) {
		this.diametro = diametro;
	}

	public String getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getPlaneta() {
		return planeta;
	}

	public void setPlaneta(String planeta) {
		this.planeta = planeta;
	}

	@Override
	public int compareTo(Asteroid o) {

		Double diametroThis = new Double(this.getDiametro());
		Double diametroObject = new Double(o.getDiametro());

		Double diametroMax = Double.max(diametroThis, diametroObject);

		if (diametroMax.equals(diametroThis)) {
			return -1;
		} else {
			return 1;
		}

	}

}
