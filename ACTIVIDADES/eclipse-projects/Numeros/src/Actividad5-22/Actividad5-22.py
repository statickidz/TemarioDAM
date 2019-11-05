# -*- coding: utf-8 -*- 
'''
Created on 27 de ene. de 2016

'''

# uso de and y or 
print"    uso de and y or    "
print "ABC" and 0
print "ABC" and {} 
print "ABC" and []
print "ABC" and ""
print "ABC" and False 
print "ABC" and None 
print "ABC" or 0
print "ABC" or {}    
print "ABC" or []    
print "ABC" or ""
print "ABC" or False    
print "ABC" or None
    
print"    and y or cortocircuito    "
def miFun(p):      
    print p
False and miFun("Valor AND") # La función no se ejecuta al ser falsa la expresión en el 1er operando
False or miFun("Valor Or") # La función se ejecuta al ser verdadera la expresión en el 2º operando 

print"    operador ?:    "
sOp1 = "a"
sOp2 = "b"  
iVal = 5
print iVal == 5 and sOp1 or sOp2
print iVal != 5 and sOp1 or sOp2 
print"    funciones en linea (lambda)    "
print (lambda x: x * 3)(6)  #función de un parámetro x, que se multiplica por tres (x*3). En este caso 18 (6)