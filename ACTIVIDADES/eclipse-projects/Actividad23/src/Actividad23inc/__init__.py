# -*- coding: utf-8 -*-
def calcula(op, val1, val2):
    """
    @param op: Operación a realizar +,-,*,/
    @param val1: Primer operando 
    @param val2: Segundo operando 
    @return: Resultado de la evaluación o false si hay error
    """
    try:  # ver siguiente punto: control de errores 
        val1 = int(val1)    
    except ValueError:
        return False
    
    try:
        val2 = int(val2) 
    except ValueError:
        return False
    
    if op == '+':
        return val1 + val2
    elif op == '-': 
        return val1 - val2
    elif op == '*':
        return val1 * val2 
    elif op == '/':
        if val2 == 0: return False
        return val1 / val2 
    else:
        return False

def printMenu():
    """
    Muestra el menú
    """
    print "+.-Suma" 
    print "-.-Resta" 
    print "*.-Multiplicación" 
    print "/.-División" 
    print "S.-Salir"
