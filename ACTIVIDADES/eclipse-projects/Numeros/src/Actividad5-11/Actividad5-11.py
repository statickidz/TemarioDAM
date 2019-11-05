# -*- coding: utf-8 -*- 
'''
Created on 18 de ene. de 2016

'''

sCadl = "Mi cadena 'ñ'\tes esta" # la ñ aparece bien por la primera línea del código fuente
sCad2 = 'Mi Cadena "ñ"\tes esta'
sCad3 = r"Mi Cadena \tñ'es esta" # raw
sCad4 = u"Mi cadena 'ñ'\tes esta" # unicode
sCad5 ="""
Mi Cadena \t'ñ'es esta"""
sCad6 = "Valor:"
sCad7 = "esta frase es de prueba" 
sCad8 ="-";
sCad9 = ("a", "b", "c"); # Arrays de cadenas, los veremos más adelante
sCad10 = "El valor de {} + {} es {}" # Las llaves denotan posición de los parámetros a sustituir 0,1,2 
# Las llaves se escapan duplicándolas ver http://docs.python.Org/2/library/string.html#formatstrings
print "Entre comillas dobles:", sCadl 
print "Entre comillas simples:", sCad2 
print "Cadena raw:", sCad3
# Funciones básicas
print"Funciones básicas"
print sCad7.capitalize()
print sCad7.center(50)
print sCad7.ljust(50)
print sCad7.rjust(50)
print sCad7.count("es")
print sCad7.find("se")
print sCad7.upper()
print sCad7.strip()
print sCad7.split(" ")
print sCad7.splitlines()
print len(sCad7)
print sCad8.join(sCad9);
print sCad10.format(1, 2, 1 +2)