package edu.cunoc.Reportes;

import java_cup.runtime.*;

%%

%class scannerSQCMS
%caseless
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
Space = {EOL} | [\t\f]
SpaceMid = [ \t\f]

ID = (_|-|"$") (_|"$"|-|[:letter:]|[:digit:])+

%%

"CONSULTAR"                   {return symbol(sym.CONSULTAR);}
"VISITAS_SITIO"               {return symbol(sym.VIS_SITIO);}
"VISITAS_PAGINA"              {return symbol(sym.VIS_PAGINA);}
"PAGINAS_POPULARES"           {return symbol(sym.PAG_POPULAR);}
"COMPONENTE"                  {return symbol(sym.COMP);}
"IMAGEN"                      {return symbol(sym.IMAGEN);}
"VIDEO"                       {return symbol(sym.VIDEO);}
"TITULO"                      {return symbol(sym.TITULO);}
"MENU"                        {return symbol(sym.MENU);}
"PARRAFO"                     {return symbol(sym.PARRAFO);}
"TODOS"                       {return symbol(sym.TODOS);}

{ID}                            {return symbol(sym.ID, new String(yytext()));}


","                    {return symbol(sym.COMA);}
"."                    {return symbol(sym.PUNTO);}
\"                     {return symbol(sym.COMILLA);}
";"                    {return symbol(sym.PC);}