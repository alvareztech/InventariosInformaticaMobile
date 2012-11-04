package com.zomwi.ii.clases;

import java.util.Date;

public class Activo {
	private String nombre;
	private int tipo;
	private int estado;
	private String descripcion;
	private Date fechaIngreso;

	public Activo() {
		super();
	}

	public Activo(String nombre, int tipo, int estado, String descripcion, Date fechaIngreso) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.estado = estado;
		this.descripcion = descripcion;
		this.fechaIngreso = fechaIngreso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
