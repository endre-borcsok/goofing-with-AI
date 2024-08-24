import tensorflow as tf
import keras
import random

def make_decision(input):
    print("ai_agent: input={input}".format(input = input))
    action = random.randint(1,4)
    print("ai_agent: action={action}".format(action = action))
    return action.to_bytes(4, 'big')