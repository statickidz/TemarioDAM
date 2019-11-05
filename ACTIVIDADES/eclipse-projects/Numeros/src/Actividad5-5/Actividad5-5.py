# -*- coding: utf-8 -*- 
'''
Created on 18 de ene. de 2016

@author: dam2
'''


iNum1 = 23 # Entero como int de c
fNum1 = 3.34 # float o reales usa doble precision
INum1 = 3L # Entero largo como long de C
iNum3 = 027 # Octal
iNum2 = 0x3F # Hexadecimal
cNum1 = 2.1 + 3.1j #Complejos
cNum2 = -4.3 - 1j
print "Valores", iNum1, fNum1, cNum1
print "Suma de los dos primeros", iNum1 + fNum1
print "Suma de los dos últimos", fNum1 + cNum1
print "Suma de los dos complejos", str(cNum1) + "+" + str(cNum2) + "=" + str(cNum1 + cNum2)
print "Octales y Hexadecimales", iNum2, iNum3