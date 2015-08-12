package br.com.curitibanahora.bd;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper 
{
	// Versão do banco de dados
	private static final int VERSAO_BANCO_DE_DADOS = 1;

	// Nome do banco de dados
	private static final String NOME_BANDO_DE_DADOS = "database";

	// Nome que a tabela vai ter
	/*private static final String TABELA_USUARIO = "usuario";*/
	private static final String TABELA_NOTICIAS = "noticias";

	/*// Colunas que a tabela NOTICIAS vai ter
	private static final String COLUNA_PRIMEIRO_ACESSO = "primeiro_acesso";
	public static final String COLUNA_ID_USER = "id";*/
	
	// Colunas que a tabela NOTICIAS vai ter
	public static final String COLUNA_ID = "id";
	public static final String COLUNA_TITULO = "titulo";
	public static final String COLUNA_CATEGORIA = "categoria";
	public static final String COLUNA_PUBDATE = "pubdate";
	private static final String COLUNA_DESCRICAO = "descricao";
	private static final String COLUNA_LINK = "link";
	private static final String COLUNA_IMAGEM = "imagem";
	public static final String COLUNA_ID_POST = "idcurrentpost";
	
	public DatabaseHandler(Context context) 
	{
		super(context, NOME_BANDO_DE_DADOS, null, VERSAO_BANCO_DE_DADOS);
	}

	// Criar tabela
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String CRIA_TABELA_USUARIO = "CREATE TABLE " + TABELA_NOTICIAS + "(" + COLUNA_ID + " " +
				"INTEGER PRIMARY KEY AUTOINCREMENT," + COLUNA_TITULO + " TEXT, " + COLUNA_CATEGORIA + " TEXT, " + COLUNA_PUBDATE + " TEXT,"
						+ " " + COLUNA_PUBDATE + " TEXT," + COLUNA_DESCRICAO + " TEXT,)";
		db.execSQL( CRIA_TABELA_USUARIO );
	/*	String CRIAR_TABELA_NOTICIAS = "CREATE TABLE " + TABELA_NOTICIAS + "(" + COLUNA_ID + " " +
				"INTEGER PRIMARY KEY AUTOINCREMENT," + COLUNA_TITULO + " TEXT," + COLUNA_CATEGORIA + " TEXT," +
						" "+COLUNA_PUBDATE+" TEXT,"+COLUNA_PRIMEIRO_ACESSO+" TEXT, "+ COLUNA_DESCRICAO+
						" TEXT,"+COLUNA_LINK+" TEXT,"+COLUNA_IMAGEM+" TEXT, "+COLUNA_ID_POST+" TEXT)";
		db.execSQL( CRIAR_TABELA_NOTICIAS );*/
	}

	// Alterar base de dados
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// Deletar tabela antiga se ela existir 
		db.execSQL("DROP TABLE IF EXISTS " + TABELA_NOTICIAS);
		//db.execSQL("DROP TABLE IF EXISTS " + TABELA_NOTICIAS);
		// Criar as tabelas novamente
		onCreate( db );
	}

	 //Adicionar um novo cliente
	/*public void addUser(UserBean userBean) 
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put( COLUNA_TITULO, userBean.getName() );
		values.put( COLUNA_CATEGORIA, userBean.getDate());
		values.put( COLUNA_PUBDATE, userBean.getSexo());
		values.put( COLUNA_nickname, userBean.getNickname());
		values.put( COLUNA_SENHA, userBean.getSenha());
		values.put( COLUNA_PRIMEIRO_ACESSO, userBean.getPrimeira());
		values.put( COLUNA_COIN, userBean.getCoin());
		values.put( COLUNA_ID_POST, userBean.getAltura());
		values.put( COLUNA_PESO, userBean.getPeso());
		values.put( COLUNA_DESCRICAO, userBean.getPeso());
		values.put( COLUNA_LINK, userBean.getCorrida());
		values.put( "musculacao", userBean.getMusculacao());
		values.put( COLUNA_IMAGEM, userBean.getCaminhada());

		// Inserir linha
		db.insert( TABELA_USER, null, values );
		
		//Recupernado o ID gerado para poder realizar a ação de Alterar na sequência
		db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM " + TABELA_USER, null);
		cursor.moveToFirst();
		int id = cursor.getInt(0);
		userBean.setId( id ); 
		db.close(); // Fechar a conexao com o banco
	}

	// Recuperar um único aluno pelo id
	public UserBean login(String ninckname, String senha) 
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query( TABELA_USER, new String[] { COLUNA_ID, COLUNA_TITULO, COLUNA_CATEGORIA,  COLUNA_PUBDATE, COLUNA_COIN, COLUNA_ID_POST, COLUNA_PESO, COLUNA_IMAGEM, COLUNA_DESCRICAO, COLUNA_LINK, "musculacao"}, COLUNA_nickname + "=? AND "+COLUNA_SENHA+" = ?", new String[] { ninckname,senha }, null, null, null, null);
		if (cursor != null) {
			
			if(!cursor.moveToFirst()){
				return null;
			}
		}
		
		UserBean ub = new UserBean();
		ub.setId( Integer.parseInt(cursor.getString(0)));
		ub.setName( cursor.getString(1) );
		ub.setDate( cursor.getString(2) );
		ub.setSexo( cursor.getString(3) );
		ub.setCoin(Integer.parseInt( cursor.getString(4) ));
		ub.setAltura(Float.parseFloat( cursor.getString(5) ));
		ub.setPeso(Float.parseFloat( cursor.getString(6) ));
		ub.setCaminhada(cursor.getString(7));
		ub.setCiclismo(cursor.getString(8));
		ub.setCorrida(cursor.getString(9));
		ub.setMusculacao(cursor.getString(10));
		
		db.close();
		cursor.close();
		
		if(ub.getName().equals("")){
			ub.setFlag(false);
		}else{
			ub.setFlag(true);
		}
		
		return ub;
	}

	public boolean verificaPrimeiroAcesso() {
		String teste=null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query( TABELA_USER, new String[] {COLUNA_PRIMEIRO_ACESSO}, COLUNA_PRIMEIRO_ACESSO + "=?", new String[] { teste }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		UserBean ub = new UserBean();
		teste= cursor.getString(0);
		db.close();
		cursor.close();
		if(teste=="false"){
			return false;
		}else{
			return true;
		}
	}
	
	

	// Recuperar todos os alunos
//	public ArrayList<AlunoBean> selecionarTodos() 
//	{
//		ArrayList<AlunoBean> clienteList = new ArrayList<AlunoBean>();
//		// Query para selecionar todos
//		String selectQuery = "SELECT  * FROM " + TABELA_ALUNOS;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// loop entre todas as linhas e adicionando todas em uma lista
//		if (cursor.moveToFirst()) {
//			do {
//				AlunoBean aluno = new AlunoBean();
//				aluno.setId( Integer.parseInt(cursor.getString(0) ) );
//				aluno.setNome( cursor.getString(1) );
//				aluno.setTelefone( cursor.getString(2) );
//				// Adicionar um cliente na lista
//				clienteList.add( aluno );
//			} while ( cursor.moveToNext() );
//		}
//		
//		db.close();
//		cursor.close();
//
//		// retornar a lista
//		return clienteList;
//	}
//
//	// Alterar um unico aluno
//	public int update(AlunoBean aluno) 
//	{
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put( COLUNA_NOME, aluno.getNome() );
//		values.put( COLUNA_TELEFONE, aluno.getTelefone() );
//		
//		int update = db.update( TABELA_ALUNOS, values, COLUNA_ID + " = ?", new String[] { String.valueOf( aluno.getId() ) } );
//		db.close();
//		return update;
//	}
//
//	// Deletar um único aluno
//	public void delete(AlunoBean aluno)
//	{
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete( TABELA_ALUNOS, COLUNA_ID + " = ?", new String[] { String.valueOf( aluno.getId() ) } );
//		db.close();
//	}
//
//	// Recuparar quantidade de alunos
//	public int getClientesCount() {
//		String countQuery = "SELECT  * FROM " + TABELA_ALUNOS;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery( countQuery, null );
//		cursor.close();
//
//		// return count
//		return cursor.getCount();
//	}
	
	//add categorias para um primeiro acesso
	public void addCategorias(){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUNA_CATEGORIAS, "Categorias");
		db.insert( TABELA_USUARIO, null, values );
		db.close(); // Fechar a conexao com o banco
	}
	
	public LinkedList<String> categorias(){
		String resultado="";
		LinkedList<String> categorias = new LinkedList<String>();
		String selectQuery = "SELECT categorias FROM " + TABELA_USUARIO;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				resultado=cursor.getString(0);
			} while ( cursor.moveToNext() );
		}
		db.close();
		cursor.close();
		
		return categorias;
	}*/

}
