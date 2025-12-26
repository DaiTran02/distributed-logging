"use client";
import { ServiceType } from "@/app/types/ServiceType";
import { useState } from "react";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import { Button } from "../ui/button";
import { CheckIcon, ChevronDownIcon } from "lucide-react";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "../ui/command";
import { cn } from "@/lib/utils";
import { twMerge } from "tailwind-merge";

interface Props {
  listServices: ServiceType[];
  className?: string;
  onChange: (value: string) => void;
}

const ServiceFilter = ({ listServices, className, onChange }: Props) => {
  const [open, setOpen] = useState(false);
  const [value, setValue] = useState("");

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className={twMerge(`w-100 justify-between ml-auto ${className}`)}
        >
          {value
            ? listServices.find((s) => s.id === value)?.serviceName
            : "All"}
          <ChevronDownIcon className="ml-2 h-4 shrink-0 opacity-50" />
        </Button>
      </PopoverTrigger>

      <PopoverContent className="w-100 p-0">
        <Command>
          <CommandInput placeholder="Find service..." />
          <CommandList>
            <CommandEmpty>Không tìm thấy</CommandEmpty>
            <CommandGroup>
              {listServices.map((service) => (
                <CommandItem
                  key={service.id}
                  value={service.id}
                  onSelect={(currentValue) => {
                    setValue(currentValue === value ? "" : currentValue);
                    setOpen(false);
                    onChange(currentValue === value ? "all" : currentValue);
                  }}
                >
                  <CheckIcon
                    className={cn(
                      "mr-2 h-4 w-4",
                      value === service.id ? "opacity-100" : "opacity-0"
                    )}
                  />

                  {service.serviceName}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
};

export default ServiceFilter;
