import tensorflow as tf
import keras
import random

def make_decision():
    action = random.randint(1,4)
    print("ai_agent: action={action}".format(action = action))
    actionBytes = action.to_bytes(4, 'big')