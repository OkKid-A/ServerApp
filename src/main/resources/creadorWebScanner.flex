package edu.cunoc.Nucleo;

import java_cup.runtime.*;import java.util.Date;

%%

%class CreadorWebLexer
%public
%cup
%line
%column

%{

   private Symbol symbol(int type) {
       return new Symbol(type, yyline+1, yycolumn+1);
   }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void error(String message) {
        System.out.println("Error lexico en linea "+(yyline+1)+", columna "+(yycolumn+1)+" : "+message);
    }
%}

EOL = \n|\r|\r\n
Space = {EOL} | [\t\f] | [\\ \\n\\r\\t\\f]
Entero =  0 | [1-9][0-9]*
Hexadecimal = #([A-Fa-f0-9]{6})
Decimal = {Entero}+ "." [1-9][0-9]*
ID = (_|-|"$") (_|"$"|-|[:letter:]|[:digit:])+
Fecha = ([0-9]{4})"-"([0-1][0-9])"-"([0-3][0-9])
Otro = (@ | - | "+" | "*"| {Fecha}| _ | # | [:letter:] | [:digit:]  )+

%%

"NUEVA_PAGINA"                  {return symbol(sym.NUEVA_PAG);}
"NUEVO_SITIO_WEB"               {return symbol(sym.NUEVA_WEB);}
"BORRAR_SITIO_WEB"              {return symbol(sym.DEL_WEB);}
"BORRAR_PAGINA"                 {return symbol(sym.DEL_PAG);}
"MODIFICAR_PAGINA"              {return symbol(sym.MOD_PAG);}
"AGREGAR_COMPONENTE"            {return symbol(sym.ADD_COMP);}
"BORRAR_COMPONENTE"             {return symbol(sym.DEL_COMP);}
"MODIFICAR_COMPONENTE"          {return symbol(sym.MOD_COMP);}
"ID"                            {return symbol(sym.PAR_ID);}
"USUARIO_CREACION"              {return symbol(sym.USER_CREACION);}
"FECHA_CREACION"                {return symbol(sym.DATE_CREACION);}
"FECHA_MODIFICACION"            {return symbol(sym.DATE_MOD);}
"USUARIO_MODIFICACION"          {return symbol(sym.USER_MOD);}
"SITIO"                         {return symbol(sym.SITIO);}
"PADRE"                         {return symbol(sym.PADRE);}
"PAGINA"                        {return symbol(sym.PAGINA);}
"CLASE"                         {return symbol(sym.CLASE);}
"TEXTO"                         {return symbol(sym.TEXTO);}
"ALINEACION"                    {return symbol(sym.ALINEACION);}
"COLOR"                         {return symbol(sym.COLOR);}
"ORIGEN"                        {return symbol(sym.ORIGEN);}
"ALTURA"                        {return symbol(sym.ALTURA);}
"ANCHO"                         {return symbol(sym.ANCHO);}
"CENTRAR"                       {return symbol(sym.CENTRAR);}
"IZQUIERDA"                     {return symbol(sym.IZQUIERDA);}
"DERECHA"                       {return symbol(sym.DERECHA);}
"JUSTIFICAR"                    {return symbol(sym.JUSTIFICAR);}

{Hexadecimal}                   {return symbol(sym.HEXA, new String(yytext()));}
{ID}                            {return symbol(sym.ID, new String(yytext()));}
{Entero}                        {return symbol(sym.ENTERO, new Integer(yytext()));}
{Fecha}                         {return symbol(sym.FECHA, new String(yytext()));}



"<"                    {return symbol(sym.MENOSQUE);}
">"                    {return symbol(sym.MAYORQUE);}
"="                    {return symbol(sym.IGUAL);}
"/"                    {return symbol(sym.DIAGONAL);}
"["                    {return symbol(sym.COR_OP);}
"]"                    {return symbol(sym.COR_CLO);}
\"                     {return symbol(sym.COMILLA);}
"|"                    {return symbol(sym.BARRA);}



[aA][cC][cC][iI][oO][nN]        {return symbol(sym.ACCION);}
[aA][cC][cC][iI][oO][nN][eE][sS]   {return symbol(sym.ACCIONES);}
[nN][oO][mM][bB][rR][eE]                        {return symbol(sym.NOMBRE);}
[aA][tT][rR][iI][bB][uU][tT][oO][sS]                     {return symbol(sym.ATRIBUTOS);}
[aA][tT][rR][iI][bB][uU][tT][oO]                      {return symbol(sym.ATRIBUTO);}
[pP][aA][rR][aA][mM][eE][tT][rR][oO][sS]                   {return symbol(sym.PARAMETROS);}
[pP][aA][rR][aA][mM][eE][tT][rR][oO]                  {return symbol(sym.PARAMETRO);}
[eE][tT][iI][qQ][uU][eE][tT][aA][sS]                  {return symbol(sym.ETIQUETAS_XML);}
[eE][tT][iI][qQ][uU][eE][tT][aA]                      {return symbol(sym.ETIQUETA);}
[iI][mM][aA][gG][eE][nN]        {return symbol(sym.IMAGEN);}
[vV][iI][dD][eE][oO]            {return symbol(sym.VIDEO);}
[pP][aA][rR][rR][aA][fF][oO]    {return symbol(sym.PARRAFO);}
[tT][iI][tT][uU][lL][oO]          {return symbol(sym.TITULO_CLASS);}
[mM][eE][nN][uU]                {return symbol(sym.MENU);}
[vV][aA][lL][oO][rR]            {return symbol(sym.VALOR);}

{Otro}                         {return symbol(sym.OTRO, new String(yytext()));}

 {Space}             {             }
 <<EOF>>             {return symbol(sym.EOF);}

 [^]                 {error("Simbolo invalido <"+yytext()+"> en linea "+(yyline+1)+" en columna "+ (yycolumn+1));}






