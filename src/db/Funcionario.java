package db;

public class Funcionario {

	private int id;
	private String nome;
	private boolean isAdmin;

	// métodos get e set para id, nome, email, endereço e dataNascimento

	

	public Funcionario(int id, String nome, boolean isAdmin) {
		super();
		this.id=id;
		this.nome=nome;
		this.isAdmin=isAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", isAdmin=" + isAdmin + "]";
	}
}