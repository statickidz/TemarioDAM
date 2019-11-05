# -*- coding: utf-8 -*- 
'''
Created on 27 de ene. de 2016

'''
print"    Funciones básicas    "
def miFun5(p1, p2):
    p1 = 1; # Variable interna no usa el parámetro
    p2[0]=1;
    print "Dentro"
    print "El valor de la externa fija", p1 
    print "El valor de la externa variable", p2
print type(miFun5) #devuelve el valor de lo pasado
print str(5) #convierte a cadena
print dir(miFun5) #devuelve el contenido de un objeto
print callable(miFun5) #determina si el objeto se puede ejecutar

print"    Punteros a funciones    "
INum1 = [1, 2, 3]
print INum1
INum1pop = getattr(INum1, "pop") # referencia al método pop de INum1, puntero a esa función
print callable(INum1pop)
INum1pop() # llamamos a la función
print INum1
