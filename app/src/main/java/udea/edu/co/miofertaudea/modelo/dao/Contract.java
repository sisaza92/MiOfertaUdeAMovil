package udea.edu.co.miofertaudea.modelo.dao;

/**
 * En esta clase se Colocan todas las constantes referentes a la base de datos como
 * el nombre de la misma, los nombres de sus tablas y columnas.
 * @author  Created by CristianCamilo.
 */
public class Contract {

    public static final  String DB_NAME = "MiOfertaUdeA.db";
    public static final int DB_VERSION = 7;
    public static final String TABLE_NAME_GRUPO = "grupo";
    public static final String TABLE_NAME_MATERIA_OFERTADA = "materiaOfertada";
    public static final String TABLE_NAME_PROGRAMA = "programa";


    /**
     * En esta clase se Colocan todas las constantes referentes a los nombres de
     * las columnas de la base de datos.
     * @author Created by CristianCamilo on 17/10/2016. sisaza92@gmail.com.
     *
     */
    public class Column {

        /**
         * Constantes que especifican los nombres de las columnas de la tabla grupo
         */
        public static final String GRUPO_ID_MATERIA = "idMateria";
        public static final String GRUPO_ID = "idGrupo";
        public static final String GRUPO_CUPO_MAXIMO = "cupoMaximo";
        public static final String GRUPO_CUPO_DISPONIBLE = "cupoDisponible";
        public static final String GRUPO_AULA = "aula";
        public static final String GRUPO_HORARIO = "horario";
        public static final String GRUPO_NOMBRE_PROFESOR = "nombreProfesor";

        /**
         * Constantes que especifican los nombres de las columnas de la tabla materiaOfertada
         */
        public static final String MATERIA_OFERTADA_CODIGO_MATERIA = "idMateria";
        public static final String MATERIA_OFERTADA_NOMBRE_MATERIA = "nombreMateria";
        public static final String MATERIA_OFERTADA_CREDITOS = "creditos";
        public static final String MATERIA_OFERTADA_GRUPO = "grupo";
        public static final String MATERIA_OFERTADA_HORARIO = "horario";

        /**
         * Constantes que especifican los nombres de las columnas de la tabla programa
         */
        public static final String PROGRAMA_CODIGO_PROGRAMA = "codigoPrograma";
        public static final String PROGRAMA_NOMBRE_PROGRAMA = "nombrePrograma";
        public static final String PROGRAMA_ESTADO = "estado";
        public static final String PROGRAMA_ULTIMO_SEMESTRE = "ultimoSemestre";
    }
}
