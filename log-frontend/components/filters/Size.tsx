"use client";

import { useState } from "react";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import { Button } from "../ui/button";
import { Check, ChevronDown, ChevronDownIcon } from "lucide-react";
import { Command, CommandGroup, CommandItem, CommandList } from "../ui/command";
import { cn } from "@/lib/utils";

const listSize = [
  { key: "10", value: 10 },
  { key: "20", value: 20 },
  { key: "50", value: 50 },
  { key: "100", value: 100 },
];

const SizeFilter = ({ onChange }: { onChange: (item: number) => void }) => {
  const [open, setOpen] = useState(false);
  const [value, setValue] = useState("10");

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant={"outline"}
          role="combobox"
          aria-expanded={open}
          className="w-30 justify-between"
        >
          {value ? listSize.find((size) => size.key === value)?.value : "10"}
          <ChevronDownIcon className="ml-2 h-4 shrink-0 opacity-50" />
        </Button>
      </PopoverTrigger>
      <PopoverContent>
        <Command>
          <CommandList>
            <CommandGroup>
              {listSize.map((size) => (
                <CommandItem
                  key={size.key}
                  value={size.key}
                  onSelect={(currentValue) => {
                    setValue(currentValue === value + "" ? "10" : currentValue);
                    onChange(
                      currentValue === value
                        ? 10
                        : (currentValue as unknown as number)
                    );
                    setOpen(false);
                  }}
                >
                  {size.value}
                  <Check
                    className={cn(
                      "ml-auto",
                      value === size.key ? "opacity-100" : "opacity-0"
                    )}
                  />
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
};

export default SizeFilter;
