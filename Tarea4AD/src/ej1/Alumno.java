package ej1;

import java.time.LocalDate;

public class Alumno {
	private String nombre, apellidos, ciclo, curso, grupo;
	private int nia;
	private char genero;
	private LocalDate fechadenacimiento;

	public Alumno(String nombre, String apellidos, String ciclo, String curso, String grupo, int nia, char genero,
			LocalDate fechadenacimiento) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.ciclo = ciclo;
		this.curso = curso;
		this.grupo = grupo;
		this.nia = nia;
		this.genero = genero;
		this.fechadenacimiento = fechadenacimiento;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public int getNia() {
		return nia;
	}

	public void setNia(int nia) {
		this.nia = nia;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public LocalDate getFechadenacimiento() {
		return fechadenacimiento;
	}

	public void setFechadenacimiento(LocalDate fechadenacimiento) {
		this.fechadenacimiento = fechadenacimiento;
	}

	@Override
	public String toString() {
		return "Alumno [nombre=" + nombre + ", apellidos=" + apellidos + ", ciclo=" + ciclo + ", curso=" + curso
				+ ", grupo=" + grupo + ", nia=" + nia + ", genero=" + genero + ", fechadenacimiento="
				+ fechadenacimiento + "]";
	}
	
	
}
