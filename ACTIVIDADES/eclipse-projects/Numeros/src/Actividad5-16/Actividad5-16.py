# -*- coding: utf-8 -*- 
'''
Created on 25 de ene. de 2016

'''

#Bloque if
sTipo = "nosocio"
if sTipo ==  "socio":    
    print "Acesso permitido"
    print "Que tenga un buen día"

#Bloque if...else
if sTipo == "socio":
    print "Acceso permitido" 
    print "Que tenga un buen día"
else: 
    print  "Acceso denegado"
    print "Lo siento"

#Bloque if...elif...elif...else
iNumero=5
if iNumero < 0: 
    print "Negativo"
elif iNumero > 0: 
    print "Positivo"
else: 
    print "Cero"

#En una línea
iVal1=3
print "cero" if (iVal1 == 0) else "Distinto de cero"