import gguf.gguf_writer  as gw

gguf_writer =gw.GGUFWriter("sine.gguf", "dense")

gguf_writer.add_architecture()
gguf_writer.add_block_count(12)
gguf_writer.add_uint32("answer", 42)  # Write a 32-bit integer
gguf_writer.add_float32("answer_in_float", 42.0)  # Write a 32-bit float
gguf_writer.add_custom_alignment(64)

gguf_writer.write_header_to_file()
#gguf_writer.write_tensors_to_file()
gguf_writer.close()
