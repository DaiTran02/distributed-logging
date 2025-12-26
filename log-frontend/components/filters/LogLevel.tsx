"use client";

import { useEffect, useState } from "react";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import { Button } from "../ui/button";
import {
  Command,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "../ui/command";
import { Check, ChevronDownIcon } from "lucide-react";
import { cn } from "@/lib/utils";

interface Props {
  onChange: (value: string) => void;
}

const levels = [
  {
    key: "INFO",
    value: "INFO",
  },
  {
    key: "WARN",
    value: "WARN",
  },
  {
    key: "ERROR",
    value: "ERROR",
  },
  {
    key: "DEBUG",
    value: "DEBUG",
  },
];

const LogLevelFilter = ({ onChange }: Props) => {
  const [open, setOpen] = useState(false);
  const [value, setValue] = useState("");

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant={"outline"}
          role="combobox"
          aria-expanded={open}
          className="w-50 justify-between"
        >
          {value
            ? levels.find((lv) => lv.key === value)?.value
            : "Find by level"}
            <ChevronDownIcon className="ml-2 h-4 shrink-0 opacity-50" />
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-50 p-0">
        <Command>
          <CommandList>
            <CommandGroup>
              {levels.map((lv) => (
                <CommandItem
                  key={lv.key}
                  value={lv.key}
                  onSelect={(currentValue) => {
                    setValue(currentValue === value ? "" : currentValue);
                    onChange(currentValue === value ? "" : currentValue);
                    setOpen(false);
                  }}
                >
                  {lv.value}
                  <Check
                    className={cn(
                      "ml-auto",
                      value === lv.key ? "opacity-100" : "opacity-0"
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

export default LogLevelFilter;
