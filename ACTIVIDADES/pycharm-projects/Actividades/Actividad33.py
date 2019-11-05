# -*- coding: utf-8 -*-
"""
    @author Adrián
"""


def miFun(p):
    """
    Mi función con un parámetro

    @version 1
    @param p parametro necesario
    @return string
    """
    if p > 2:
        return "Es mayor que dos"
    else:
        return "Es menor que dos"


print miFun(4)
