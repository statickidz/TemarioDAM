# -*- coding: utf-8 -*- 
'''
Created on 18 de ene. de 2016

@author: dam2
'''

#    Operadores +,-+*,/,//,**,%
from decimal import Decimal
iNuml = 23  # Entero como int de C
fNuml = 3.34  # float o reales usa doble precision
INuml = 3L  # Entero largo como long de C
print "multiplicacion:" + str(iNuml) + "*" + str(fNuml) + "=", iNuml * fNuml
print "division:" + str(iNuml) + "/Decimal(" + str(fNuml) + ")=", iNuml / Decimal(fNuml)
print "division:" + str(iNuml) + "/" + str(fNuml) + "=", iNuml / fNuml
print "division entera:" + str(iNuml) + "//" + str(fNuml) + "=", iNuml // fNuml
print "division (solo enteros):" + str(iNuml) + "/" + str(INuml) + "=", iNuml / INuml # Determina division entera 
print "modulo: " + str(iNuml) + "%" + str(INuml) + "=", iNuml % INuml 
print "exponente:" + str(INuml) + "**" + str(INuml) + "=", INuml ** INuml