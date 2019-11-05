# -*- coding: utf-8 -*- 
'''
Created on 27 de ene. de 2016

'''
#Variables globales y locales, sólo las listas y diccionarios mantienen cambio
print"    variables globales y locales    "
varExtFija = 9; 
varExtMod = [0, 0] 
def miFun4():
    varExtFija = 1;
    varExtMod[0] = 1;
    print "Dentro"
    print "El valor de la externa fija", varExtFija 
    print "El valor de la externa variable", varExtMod 

print "Antes"
print "El valor de la externa fija", varExtFija
print "El valor de la externa variable", varExtMod
miFun4()
print "Después"
print "El valor de la externa fija", varExtFija 
print "El valor de la externa variable", varExtMod

varExtFija1 = 9;
varExtMod1 = [0, 0] 
def miFun5(p1, p2):
    p1 = 1; # Variable interna no usa el parámetro
    p2[0]=1;
    print "Dentro"
    print "El valor de la externa fija", p1 
    print "El valor de la externa variable", p2
    
print "Antes"
print "El valor de la externa fija", varExtFija1 
print "El valor de la externa variable", varExtMod1 
miFun5(varExtFija1, varExtMod1) 
print "Después"
print "El valor de la externa fija", varExtFija1 
print "El valor de la externa variable", varExtMod1