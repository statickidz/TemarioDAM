# -*- coding: utf-8 -*- 
'''
Created on 27 de ene. de 2016

'''
# definición de la función
print "    definición  de funciones    "
def miFun(param):
    if param > 2: return "Mayor de dos"
    else: return "Menor o igual que dos"
print miFun(2) 
print miFun(3) 
print miFun("abcd")

# parámetros optativos
print "    parámetros optativos o por defecto    "
def miFun2(par1, par2="Hola", par3=(True, 2)): 
    print "Parámetros:", par1, par2, par3
miFun2("Adios")
miFun2(23, "Adiós")
miFun2(4, par3="Adios")
miFun2(2 + 5j, par3=23, par2="Par1")

# parámetros variables (el parámetro es una tupla si *,diccionario si **)
print"    parámetros variables    "
def miFun3(*params): 
    for ele in params: print ele
miFun3("a") 
miFun3(1, 2, "abc")